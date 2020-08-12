window.onload = function() {
        //得到所有的文件
        getAllFile();
        //得到所有图片
        getAllImages();
        //得到用户信息
        getUserInfoFromAfter()

    }
    //上传公共文件
function upPublicFile() {
    console.log("触发上传");
    var files = $('#file').prop('files');
    var formData = new FormData();
    formData.append('file', files[0]);
    $.ajax({
        type: 'post',
        url: "http://localhost:8080/admin/upPublicFile",
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success: function(ret) {
            alert(ret);
        }
    });
}
//得到所有文件
function getAllFile() {
    $.ajax({
        type: 'post',
        url: "http://localhost:8080/admin/getAllFile",
        data: null,
        cache: false,
        success: function(ret) {
            console.log(ret.data[0]);
            var after = document.getElementById('right_table').firstElementChild;
            // var tbody = document.createElement("tbody");
            for (var i = 0; i < ret.data.length; i++) {
                var tr = document.createElement("tr");
                tr.innerHTML = '<td>' + ret.data[i].fileName + '<a onclick="downloadfile()" id="' + ret.data[i].fileid + '"><span class="glyphicon glyphicon-save"></span></a></td><td>' + ret.data[i].fileSize + 'B' + '</td><td>' + ret.data[i].uptime + '</td>'
                after.appendChild(tr)
            }
        }
    })
}

function getUserInfoFromAfter() {
    $.ajax({
        type: 'post',
        url: "http://localhost:8080/getUserInfo",
        data: null,
        cache: false,
        success: function(ret) {
            document.getElementById('head_portrait').children[1].innerHTML = ret.data
            document.getElementById("showUserInfo")
                .children[0].children[1].innerHTML = ret.data
        }
    })
}

function getAllImages() {
    $.ajax({
        type: 'post',
        url: "http://localhost:8080/admin/getAllImages",
        data: null,
        success: function(ret) {
            console.log("成功");
            var images = document.getElementById('images')
            console.log(ret.data);
            for (var i = 0; i < ret.data.length; i++) {
                var div = document.createElement("div");
                // 下属img
                var img = document.createElement("img")
                img.src = "http://localhost:8080/admin/getImageByfileId/" + ret.data[i].fileid;
                img.className = "imageSize left_float";
                div.appendChild(img);
                images.appendChild(div);
            }
        }
    })
}

function showUserInfo() {
    var showUserInfo = document.getElementById('showUserInfo')
    showUserInfo.style.display = "block";
}

function showout() {
    var showUserInfo = document.getElementById('showUserInfo')
    showUserInfo.style.display = "none";
}

function downloadfile() {
    var that = event.currentTarget.id;
    // 开始下载
    console.log("ajaxDownloadSynchronized");
    // 下载地址
    var url = "http://localhost:8080/file/download";
    // 下载文件id
    var fileid = that;
    // 模拟表单下载
    var form = $("<form></form>").attr("action", url).attr("method", "post");
    form.append($("<input></input>").attr("type", "hidden").attr("name", "fileid").attr("value", fileid));
    form.appendTo('body').submit().remove();
}

function showAllFile() {
    document.getElementById('outShow').style.display = "block"
    document.getElementById('showWindows').style.display = "block";
    document.getElementById('Allfile').style.display = "block"
}

function outShow() {
    document.getElementById('outShow').style.display = "none"
    document.getElementById('showWindows').style.display = "none";
    document.getElementById('Allfile').style.display = "none"
}

function showAllImage() {
    document.getElementById('outimg').style.display = "block"
    document.getElementById('allimage').style.display = "block"
}

function returnPage() {
    document.getElementById('outimg').style.display = "none"
    document.getElementById('allimage').style.display = "none"
}

function UserInfo() {
    alert("功能未完成");
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