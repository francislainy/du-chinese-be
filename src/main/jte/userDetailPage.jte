@import com.francislainy.duchinesebe.model.User
@param User user

<!doctype HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="/main.css" />
    <script src="http://localhost:35729/livereload.js"></script>
</head>
<body class="bg-slate-50">
<header class="bg-red-500 container mx-auto">
    <h1 class="text-2xl font-bold p-8 text-blue-600">User Detail Page for ${user.getUsername()}</h1>
</header>
</body>
</html>
