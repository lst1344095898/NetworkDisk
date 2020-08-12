window.onload = function() {
        var username = "lst";
        $.ajax({
            type: 'post',
            url: "http://localhost:8080/file/getFileByUserName",
            data: { "username": username },
            cache: false,
            success: function(ret) {
                console.log(ret.data[0]);
                var after = document.getElementById('right_table').firstElementChild;
                // var tbody = document.createElement("tbody");
                for (var i = 0; i < ret.data.length; i++) {
                    var tr = document.createElement("tr");
                    tr.innerHTML = '<td>' + ret.data[i].fileName + '<a onclick="downloadfile()" id="' + ret.data[i].fileid + '"><span class="glyphicon glyphicon-save"></span></a></td><td>' + ret.data[i].fileSize + 'B' + '</td>'
                    after.appendChild(tr)
                }
            }
        });
        $.ajax({
            type: 'post',
            url: "http://localhost:8080/getUserInfo",
            data: null,
            cache: false,
            success: function (ret){
                document.getElementById('head_portrait').children[1].innerHTML=ret.data
                document.getElementById("showUserInfo")
                    .children[0].children[1].innerHTML=ret.data
            }
        })

    }
    // 下载
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

function fileUpload() {
    console.log("触发上传");
    var files = $('#file').prop('files');
    var formData = new FormData();
    formData.append('file', files[0]);
    $.ajax({
        type: 'post',
        url: "http://localhost:8080/file/upload",
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        success: function(ret) {
            alert(ret);
        }
    });
};


function downloadFileByForm() {
    // 开始下载
    console.log("ajaxDownloadSynchronized");
    // 下载地址
    var url = "http://localhost:8080/file/download";
    // 下载名
    var fileName = "testAjaxDownload.txt";
    // 模拟表单下载
    var form = $("<form></form>").attr("action", url).attr("method", "post");
    form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
    form.appendTo('body').submit().remove();
}

function showUserInfo() {
    var showUserInfo = document.getElementById('showUserInfo')
    showUserInfo.style.display = "block";
}

function showout() {
    var showUserInfo = document.getElementById('showUserInfo')
    showUserInfo.style.display = "none";
}