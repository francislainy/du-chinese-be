@import com.francislainy.duchinesebe.model.User
@param User user

<!doctype HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="/main.css"/>
    <script src="http://localhost:35729/livereload.js"></script>
    <script>
        function resetProgress(userId) {
            var url = '/api/v1/users/reset-progress/' +  userId;
            fetch(url, {
                method: 'POST'
            }).then(response => {
                if (response.ok) {
                    alert('User progress has been reset.');
                    location.reload();
                } else {
                    alert('Failed to reset user progress.');
                }
            });
        }
    </script>
</head>
<body class="bg-gray-100 min-h-screen">
<header class="bg-black container mx-auto py-4 shadow-md">
    <h1 class="text-3xl font-bold text-center text-white">User Detail Page for ${user.getUsername()}</h1>
</header>

<main class="container mx-auto p-8">
    <section class="bg-white p-6 rounded shadow-md">
        <h2 class="text-xl font-semibold mb-4">User Details</h2>
        <p><strong>Username:</strong> ${user.getUsername()}</p>
        <p><strong>Email:</strong> ${user.getUsername()}</p>
        <p><strong>Role:</strong> <span id="user-role">${user.getRole()}</span></p>

        <!-- Change Role Button -->
        <button
                class="bg-blue-500 text-white px-4 py-2 rounded mt-4 hover:bg-blue-700"
                onclick="changeUserRole('${user.getUsername()}')">
            Change Role to ${user.getRole().equals("Admin") ? "Basic User" : "Admin"}
        </button>

        <!-- Reset Password Button -->
        <button
                class="bg-blue-500 text-white px-4 py-2 rounded mt-4 hover:bg-blue-700 mb-6"
                onclick="triggerPasswordReset('${user.getUsername()}')">
            Reset Password
        </button>

        <h2 class="text-xl font-semibold mt-8 mb-4 text-red-600">Dangerous Section</h2>
        <div class="mt-8 pb-8">
            <!-- Reset Progress Button -->
            <!-- Reset Progress Button -->
            <div style="display: block; width: fit-content;">
                <button
                        class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-green-700"
                        onclick="resetProgress('${String.valueOf(user.getId())}')">
                    Reset Progress
                </button>
            </div>

            <!-- Delete Account Button -->
            <div style="display: block; width: fit-content; margin-top: 1rem;">
                <button
                        class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700"
                        onclick="deleteUserAccount('${user.getUsername()}')">
                    Delete Account
                </button>
            </div>
        </div>
    </section>
</main>
</body>
</html>


