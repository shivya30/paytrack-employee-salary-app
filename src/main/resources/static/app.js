// ========================================
// PAYTRACK - EMPLOYEE SALARY APP
// ========================================


// ========================================
// GLOBAL VARIABLE
// ========================================

let selectedEmployeeId = null;


// ========================================
// LOAD DASHBOARD
// ========================================

async function loadDashboard() {

    try {

        const response = await fetch("/api/dashboard");

        if (!response.ok) {
            throw new Error("Failed to load dashboard");
        }

        const data = await response.json();


        document.getElementById("totalEmployees").textContent =
            data.totalEmployees;


        document.getElementById("totalPayroll").textContent =
            "₹ " + Number(data.totalPayroll).toLocaleString();


        document.getElementById("averageSalary").textContent =
            "₹ " + Number(data.averageSalary).toLocaleString();

    }

    catch (error) {

        console.error("Dashboard error:", error);

    }

}


// ========================================
// LOAD ANALYTICS
// ========================================

async function loadAnalytics() {

    try {

        const response = await fetch("/api/analytics");

        if (!response.ok) {
            throw new Error("Failed to load analytics");
        }

        const data = await response.json();


        // ========================================
        // MEDIAN
        // ========================================

        document.getElementById("medianSalary").textContent =
            "₹ " + Number(data.medianSalary).toLocaleString();


        // ========================================
        // HIGHEST SALARY
        // ========================================

        document.getElementById("highestSalary").textContent =
            "₹ " + Number(data.highestSalary).toLocaleString();


        // ========================================
        // LOWEST SALARY
        // ========================================

        document.getElementById("lowestSalary").textContent =
            "₹ " + Number(data.lowestSalary).toLocaleString();


        // ========================================
        // SALARY DISTRIBUTION
        // ========================================

        const salaryContainer =
            document.getElementById("salaryDistribution");

        salaryContainer.innerHTML = "";


        const salaryRanges = data.salaryRanges;

        const maxSalaryRange =
            Math.max(...Object.values(salaryRanges));


        for (const [range, count]
            of Object.entries(salaryRanges)) {

            const percentage =
                maxSalaryRange === 0
                    ? 0
                    : (count / maxSalaryRange) * 100;


            salaryContainer.innerHTML += `

                <div class="chart-row">

                    <div class="chart-label">
                        ${range}
                    </div>

                    <div class="chart-bar-container">

                        <div
                            class="chart-bar"
                            style="width:${percentage}%">
                        </div>

                    </div>

                    <div class="chart-value">
                        ${count}
                    </div>

                </div>

            `;
        }


        // ========================================
        // PAYROLL BY COUNTRY
        // ========================================

        const countryContainer =
            document.getElementById("countryPayroll");

        countryContainer.innerHTML = "";


        for (const [country, salary]
            of Object.entries(data.payrollByCountry)) {

            countryContainer.innerHTML += `

                <div class="data-row">

                    <span>
                        ${country}
                    </span>

                    <strong>
                        ₹ ${Number(salary).toLocaleString()}
                    </strong>

                </div>

            `;
        }


        // ========================================
        // PAYROLL BY DEPARTMENT
        // ========================================

        const departmentContainer =
            document.getElementById("departmentPayroll");

        departmentContainer.innerHTML = "";


        for (const [department, salary]
            of Object.entries(data.payrollByDepartment)) {

            departmentContainer.innerHTML += `

                <div class="data-row">

                    <span>
                        ${department}
                    </span>

                    <strong>
                        ₹ ${Number(salary).toLocaleString()}
                    </strong>

                </div>

            `;
        }

    }

    catch (error) {

        console.error("Analytics error:", error);

    }

}


// ========================================
// LOAD EMPLOYEES
// ========================================

async function loadEmployees() {

    try {

        const search =
            document.getElementById("search").value.trim();

        const country =
            document.getElementById("country").value.trim();

        const department =
            document.getElementById("department").value.trim();


        const params = new URLSearchParams();


        if (search) {

            params.append("search", search);

        }


        if (country) {

            params.append("country", country);

        }


        if (department) {

            params.append("department", department);

        }


        const response =
            await fetch("/api/employees?" + params.toString());


        if (!response.ok) {

            throw new Error("Failed to load employees");

        }


        const employees =
            await response.json();


        const table =
            document.getElementById("employeeTable");


        table.innerHTML = "";


        // ========================================
        // SHOW ONLY FIRST 50 EMPLOYEES
        // ========================================

        const visibleEmployees =
            employees.slice(0, 50);


        visibleEmployees.forEach(employee => {

            table.innerHTML += `

                <tr>

                    <td>
                        ${employee.id}
                    </td>

                    <td>
                        ${employee.employeeCode}
                    </td>

                    <td>
                        ${employee.name}
                    </td>

                    <td>
                        ${employee.country}
                    </td>

                    <td>
                        ${employee.department}
                    </td>

                    <td>
                        ₹ ${Number(employee.salary).toLocaleString()}
                    </td>

                    <td>
                        ${employee.currency}
                    </td>

                    <td>

                        <button
                            class="edit-button"
                            onclick="openSalaryForm(${employee.id})">

                            Edit

                        </button>


                        <button
                            class="delete-button"
                            onclick="deleteEmployee(${employee.id})">

                            Delete

                        </button>

                    </td>

                </tr>

            `;

        });


        // ========================================
        // SHOW RECORD COUNT
        // ========================================

        if (employees.length > 50) {

            table.innerHTML += `

                <tr>

                    <td
                        colspan="8"
                        style="text-align:center; padding:20px;">

                        Showing first 50 of
                        ${employees.length}
                        employees

                    </td>

                </tr>

            `;

        }


        // ========================================
        // NO DATA MESSAGE
        // ========================================

        if (employees.length === 0) {

            table.innerHTML = `

                <tr>

                    <td
                        colspan="8"
                        style="text-align:center; padding:20px;">

                        No employees found.

                    </td>

                </tr>

            `;

        }

    }

    catch (error) {

        console.error("Employee loading error:", error);

    }

}


// ========================================
// ADD EMPLOYEE POPUP
// ========================================

function openAddEmployeeForm() {

    const modal =
        document.getElementById("employeeModal");


    if (!modal) {

        console.error("employeeModal not found");

        return;

    }


    modal.style.display = "flex";

}


// ========================================
// CLOSE ADD EMPLOYEE POPUP
// ========================================

function closeEmployeeForm() {

    const modal =
        document.getElementById("employeeModal");


    if (modal) {

        modal.style.display = "none";

    }

}


// ========================================
// ADD EMPLOYEE
// ========================================

async function addEmployee(event) {

    event.preventDefault();


    const employee = {

        employeeCode:
            document.getElementById("employeeCode").value.trim(),

        name:
            document.getElementById("employeeName").value.trim(),

        country:
            document.getElementById("employeeCountry").value.trim(),

        department:
            document.getElementById("employeeDepartment").value.trim(),

        salary:
            Number(
                document.getElementById("employeeSalary").value
            ),

        currency:
            document
                .getElementById("employeeCurrency")
                .value
                .trim()
                .toUpperCase()

    };


    try {

        const response =
            await fetch("/api/employees", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(employee)

            });


        if (!response.ok) {

            throw new Error("Failed to add employee");

        }


        alert("Employee added successfully!");


        document
            .getElementById("employeeForm")
            .reset();


        // Default currency again
        document
            .getElementById("employeeCurrency")
            .value = "INR";


        closeEmployeeForm();


        await loadEmployees();

        await loadDashboard();

        await loadAnalytics();

    }

    catch (error) {

        console.error("Add employee error:", error);

        alert("Failed to add employee.");

    }

}


// ========================================
// OPEN UPDATE SALARY POPUP
// ========================================

function openSalaryForm(employeeId) {

    selectedEmployeeId = employeeId;


    const modal =
        document.getElementById("salaryModal");


    if (!modal) {

        console.error("salaryModal not found");

        return;

    }


    modal.style.display = "flex";


    // Focus salary input
    const salaryInput =
        document.getElementById("newSalary");


    if (salaryInput) {

        salaryInput.focus();

    }

}


// ========================================
// CLOSE UPDATE SALARY POPUP
// ========================================

function closeSalaryForm() {

    const modal =
        document.getElementById("salaryModal");


    if (modal) {

        modal.style.display = "none";

    }


    selectedEmployeeId = null;

}


// ========================================
// UPDATE SALARY
// ========================================

async function updateSalary(event) {

    event.preventDefault();


    if (selectedEmployeeId === null) {

        alert("No employee selected.");

        return;

    }


    const salary =
        document.getElementById("newSalary").value;


    if (!salary || Number(salary) <= 0) {

        alert("Please enter a valid salary.");

        return;

    }


    try {

        const response =
            await fetch(
                `/api/employees/${selectedEmployeeId}/salary?salary=${salary}`,
                {
                    method: "PUT"
                }
            );


        if (!response.ok) {

            throw new Error("Failed to update salary");

        }


        alert("Salary updated successfully!");


        document
            .getElementById("salaryForm")
            .reset();


        closeSalaryForm();


        await loadEmployees();

        await loadDashboard();

        await loadAnalytics();

    }

    catch (error) {

        console.error("Update salary error:", error);

        alert("Failed to update salary.");

    }

}


// ========================================
// DELETE EMPLOYEE
// ========================================

async function deleteEmployee(employeeId) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this employee?"
        );


    if (!confirmed) {

        return;

    }


    try {

        const response =
            await fetch(
                `/api/employees/${employeeId}`,
                {
                    method: "DELETE"
                }
            );


        if (!response.ok) {

            throw new Error("Failed to delete employee");

        }


        alert("Employee deleted successfully!");


        await loadEmployees();

        await loadDashboard();

        await loadAnalytics();

    }

    catch (error) {

        console.error("Delete employee error:", error);

        alert("Failed to delete employee.");

    }

}


// ========================================
// CLOSE MODALS WHEN CLICKING OUTSIDE
// ========================================

window.addEventListener("click", function (event) {

    const employeeModal =
        document.getElementById("employeeModal");


    const salaryModal =
        document.getElementById("salaryModal");


    if (event.target === employeeModal) {

        closeEmployeeForm();

    }


    if (event.target === salaryModal) {

        closeSalaryForm();

    }

});


// ========================================
// INITIAL LOAD
// ========================================

loadDashboard();

loadAnalytics();

loadEmployees();