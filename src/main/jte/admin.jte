@import com.francislainy.duchinesebe.model.User
@import java.util.List
@param List<User> users

<!doctype HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/main.css">
</head>
<body class="bg-gray-100 min-h-screen">
<header class="bg-red-500 container mx-auto py-4">
    <h1 class="text-3xl font-bold text-center text-white">Admin Dashboard</h1>
</header>
<main class="container mx-auto p-8">
    <h2 class="text-2xl font-semibold mb-4">User List</h2>
    <div id="user-list" class="overflow-x-auto bg-white shadow-md rounded-lg">
        <table class="min-w-full bg-white">
            <thead class="bg-gray-800 text-white">
            <tr>
                <th class="py-2 px-4">Username</th>
                <th class="py-2 px-4">Role</th>
            </tr>
            </thead>
            <tbody>
            <!-- Iterate over users and display them -->
            @for(User user : users)
                <tr class="border-b">
                    <td class="py-2 px-4">${user.getUsername()}</td>
                    <td class="py-2 px-4">${user.getRole()}</td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
