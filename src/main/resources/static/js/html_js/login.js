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
        success: function(ret) {
            console.log(ret)
            if (ret.state_id) {
                if (ret.data==="admin"){
                    console.log("admin")
                    location.href = "http://localhost:8080/html/admin";
                    }else {
                    location.href = "http://localhost:8080/html/home";
                }
            }else {
                alert("密码错误")
            }
        },
        error: function (ret){
            console.log("error");
        }
    });
}