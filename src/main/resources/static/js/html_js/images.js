window.onload = function() {
    $.ajax({
            type: 'post',
            url: "http://localhost:8080/file/getAllImagesByUsername",
            data: JSON.stringify({"images":"images"}),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function(ret) {
                console.log("成功");
                var images = document.getElementById('images')
                console.log(ret.data);
                for (var i = 0; i < ret.data.length; i++) {
                    var div = document.createElement("div");
                    // 下属img
                    var img = document.createElement("img")
                    img.src = "http://localhost:8080/file/images/getImageByfileId/" + ret.data[i].fileid;
                    img.className = "imageSize left_float";
                    div.appendChild(img);
                    images.appendChild(div);
                }
            }
        })
        // image1.style.width = "300px";
        // image1.style.height = "300px"
        // image1.src = 'http://localhost:8080/file/images/getImages/' + imageid
}

function showUserInfo() {
    var showUserInfo = document.getElementById('showUserInfo')
    showUserInfo.style.display = "block";
}

function showout() {
    var showUserInfo = document.getElementById('showUserInfo')
    showUserInfo.style.display = "none";
}

// 修改密码
function editPassword() {
    document.getElementById('editPassword').style.display = "block"
    document.getElementById('back_plate').style.display = "block"
}

function Yes() {
    var password = document.getElementById('newpassword').value
    var data={"password" : password}
    $.ajax({
        type: 'post',
        url: "http://localhost:8080/editPassword",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: 'application/json;charset=UTF-8',
        success: function(ret) {
            if (ret) {
                document.getElementById('editPassword').style.display = "none"
                document.getElementById('back_plate').style.display = "none"
                alert('密码修改成功')
            } else {
                alert('密码修改失败')
            }
        }
    })
}
//取消
function  No(){
    document.getElementById('editPassword').style.display = "none"
    document.getElementById('back_plate').style.display = "none"
}
function outLogin(){
    $.ajax({
        type:'post',
        url: "http://localhost:8080/cleanSession",
        success:  function (ret){
            if (ret){
                location.href="http://localhost:8080/"
            }else{
                alert("请关闭浏览器");
            }
        }
    })
}