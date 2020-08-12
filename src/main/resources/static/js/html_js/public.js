window.onload = function() {
        $.ajax({
            type: 'post',
            url: "http://localhost:8080/file/getFileByUserName",
            data: {"public": "public"},
            success: function(ret) {
                console.log(ret.data[0]);
                let showFile = document.getElementById('showFile');
                showFile.style.display = "none";
                var after = document.getElementById('showFileTable');
                for (var i = 0; i < ret.data.length; i++) {
                    var tr = document.createElement("tr");
                    tr.innerHTML = '<td>' + ret.data[i].fileName + '<a onclick="downloadfile()" id="' + ret.data[i].fileid + '"><span class="glyphicon glyphicon-save"></span></a></td><td>' + ret.data[i].fileSize + 'B' + '</td><td>'+ ret.data[i].uptime +'</td>'
                    after.firstElementChild.appendChild(tr)
                }
            }
        })
        $.ajax({
            type: 'post',
            url: "http://localhost:8080/file/getAllImagesByUsername",
            data: {"public": "public"},
            success: function(ret) {
                console.log("成功");
                var images = document.getElementById('images')
                images.style.display = "none";
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
    }
    // 下载文件按钮
function filedown() {
    var pos = 0;
    var size = 0;
    document.getElementById('file').children[1].style.display = "none";
    document.getElementById('img').children[1].style.display = "none";
    // 计时器
    var id = setInterval(fram, 10);
    document.getElementById('showFileTable').style.display = "none"
    document.getElementById('showFile').style.width = "10px"
    document.getElementById('showFile').style.height = "10px"
    document.getElementById('showFile').style.display = "block";
    var show = setInterval(ShowFile, 45);
    // 图片缩小
    function fram() {
        if (pos == 49) {
            clearInterval(id);
            document.getElementById('file').style.display = "none";
            document.getElementById('img').style.display = "none"
            pos = 0;
        } else {
            pos++;
            document.getElementById('file').children[0].style.width = (50 - pos) + "px";
            document.getElementById('file').children[0].style.height = (50 - pos) + "px";
            document.getElementById('img').children[0].style.width = (50 - pos) + "px";
            document.getElementById('img').children[0].style.height = (50 - pos) + "px";
        }
    }
    // 展示文件表格
    function ShowFile() {
        if (size == 20) {
            clearInterval(show);
            showFileTable.style.width = "900px";
            showFileTable.style.height = "500px"
            showFileTable.style.display = "block";
            var img = document.createElement("img");
            img.src = "/img/X.png"
            img.className = "X_img";
            img.onclick = showout;
            showFile.firstElementChild.appendChild(img)
        } else {
            size++;
            showFile.style.width = size * 45 + "px";
            showFile.style.height = size * 25 + "px";
        }

    }

}
// 退出文件表格
function showout() {
    document.getElementById('showFile').style.display = "none";
    var pos = 0;
    var id = setInterval(fram1, 10);
    document.getElementById('file').style.display = "block";
    document.getElementById('img').style.display = "block";

    function fram1() {
        if (pos == 49) {
            clearInterval(id);
            document.getElementById('file').children[1].style.display = "block";
            document.getElementById('img').children[1].style.display = "block";
            pos = 0;
        } else {
            pos++;
            document.getElementById('file').children[0].style.width = pos + "px";
            document.getElementById('file').children[0].style.height = pos + "px";
            document.getElementById('img').children[0].style.width = pos + "px";
            document.getElementById('img').children[0].style.height = pos + "px";
        }
    }
}

function imgdown() {
    var pos = 0;
    var id = setInterval(fram, 10);
    document.getElementById('file').children[1].style.display = "none";
    document.getElementById('img').children[1].style.display = "none";

    function fram() {
        if (pos == 49) {
            clearInterval(id);
            document.getElementById('file').style.display = "none";
            document.getElementById('img').style.display = "none"
            pos = 0;
            document.getElementById('images').style.display = "block";
            document.getElementById('login').style.display = "none";
            document.getElementById('outimg').style.display = "block";
        } else {
            pos++;
            document.getElementById('file').children[0].style.width = (50 - pos) + "px";
            document.getElementById('file').children[0].style.height = (50 - pos) + "px";
            document.getElementById('img').children[0].style.width = (50 - pos) + "px";
            document.getElementById('img').children[0].style.height = (50 - pos) + "px";
        }
    }
}

function returnPage() {
    // 图标的显现
    document.getElementById('images').style.display = "none";
    document.getElementById('login').style.display = "block";
    document.getElementById('outimg').style.display = "none";
    var pos = 0;
    var id = setInterval(fram, 10);
    document.getElementById('file').style.display = "block";
    document.getElementById('img').style.display = "block";

    function fram() {
        if (pos == 49) {
            clearInterval(id);
            document.getElementById('file').children[1].style.display = "block";
            document.getElementById('img').children[1].style.display = "block";
            pos = 0;
        } else {
            pos++;
            document.getElementById('file').children[0].style.width = pos + "px";
            document.getElementById('file').children[0].style.height = pos + "px";
            document.getElementById('img').children[0].style.width = pos + "px";
            document.getElementById('img').children[0].style.height = pos + "px";
        }
    }
}
// 下载文件
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