<!doctype HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="/main.css">
</head>
<body class="bg-gray-100 h-screen flex items-center justify-center">
<div class="w-full max-w-xs">
    <div class="bg-white shadow-md rounded-t px-8 pt-6 pb-4">
        <h2 class="text-center text-2xl font-bold mb-4">Login</h2>
    </div>
    <form id="loginForm" class="bg-white shadow-md rounded-b px-8 pt-6 pb-8 mb-4" onsubmit="submitLoginForm(event)">
        <div class="mb-4">
            <label for="username" class="block text-gray-700 text-sm font-bold mb-2">Username:</label>
            <input type="text" id="username" name="username" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
        </div>
        <div class="mb-6">
            <label for="password" class="block text-gray-700 text-sm font-bold mb-2">Password:</label>
            <input type="password" id="password" name="password" required class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline">
        </div>
        <div class="flex items-center justify-between">
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                Login
            </button>
        </div>
    </form>
</div>

<script>
    async function submitLoginForm(event) {
        event.preventDefault();
        const form = document.getElementById('loginForm');
        const formData = new FormData(form);
        const data = {
            username: formData.get('username'),
            password: formData.get('password')
        };

        const response = await fetch('/api/v1/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            window.location.href = '/admin';
        } else {
            alert('Login failed');
        }
    }
</script>
</body>
</html>
