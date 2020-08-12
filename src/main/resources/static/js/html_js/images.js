window.onload = function() {
    //
    // var image1 = document.getElementById('images1')
    // var imageid = '1';
    $.ajax({
            type: 'post',
            url: "http://localhost:8080/file/getAllImages",
            data: null,
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