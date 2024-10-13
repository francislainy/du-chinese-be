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
<header class="bg-black container mx-auto py-4 shadow-md">
    <h1 class="text-3xl font-bold text-center text-white">Admin Dashboard</h1>
</header>
<main class="container mx-auto pt-6 pb-8">
    <h2 class="text-2xl font-semibold mb-4 text-gray-700">User List</h2>
    <div id="user-list" class="overflow-x-auto bg-white shadow-md rounded-lg p-6">
        <table class="min-w-full bg-white">
            <thead class="bg-gray-200 text-gray-800">
            <tr>
                <th class="py-2 px-4 text-left">Username</th>
                <th class="py-2 px-4 text-left">Role</th>
            </tr>
            </thead>
            <tbody>
            @for(User user : users)
                <tr class="border-b hover:bg-gray-100 transition duration-150 ease-in-out">
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

