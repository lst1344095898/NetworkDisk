function login() {
    var username = document.getElementById('username')
    var password = document.getElementById('password')
    var data = { "username": username.value, "password": password.value }
    console.log(data);
    $.ajax({
        type: 'post',
        url: "http://localhost:8080/login",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: 'application/json;charset=UTF-8',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function(ret) {
            location.href = "http://localhost:8080/home";
        }
    });
}