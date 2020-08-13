// 选取name=file的input标签--获取到上传文件input
var fileBtn = $("input[name=file]");
// 获得已完成上传的进度条
var processBar = $("#progressBar");
//获取上传按钮
var uploadBtn = $("input[name=upload]");
//取消上传
var canelBtn = $("input[name=cancelUpload]");
var ot; //上传开始时间
var oloaded; //已上传文件大小
//当上传文件选中后
fileBtn.change(function() {
    //
    var fileObj = fileBtn.get(0).files[0]; //js获取文件对象
    console.log(fileObj);
    if (fileObj) { //判断空
        //得到文件大小转换文件大小数值
        var fileSize = getSize(fileObj.size);
        // 将文件名字赋值到相应位置
        $("label[name=upfileName]").text('文件名：' + fileObj.name);
        // 将文件大小赋值到相应位置
        $("label[name=upfileSize]").text('文件大小：' + fileSize);
        // 将文件类型赋值到相应位置
        $("label[name=upfileType]").text('文件类型：' + fileObj.type);
        // 给上传按钮添加禁用属性取消静用
        uploadBtn.attr('disabled', false);
    }
});
// 上传文件按钮点击的时候
function downloadfile(htmlName) {
    // 进度条归零
    setProgress(0);
    // 上传按钮禁用
    $(this).attr('disabled', true);
    // 进度条显示
    showProgress();
    // 上传文件
    uploadFile();
};
// 上传文件
function uploadFile() {
    // 上传路径
    var url = "http://localhost:8080/file/upload";
    // 获取文件对象
    var fileObj = fileBtn.get(0).files[0];

    //判断是否选中文件
    if (fileObj == null) {
        alert("请选择文件");
        return;
    }
    // FormData 对象 将数据编译成键值对
    var form = new FormData();
    // 在对象中插入最后插入文件的对象
    form.append('file', fileObj); // 文件对象
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    //true为异步处理 // 加入请求类型和路径 和异步处理
    xhr.open('post', url + '?username=index', true);
    //
    // xhr.setRequestHeader('content-type', 'multipart/form-data');
    //上传开始执行方法onloadstart在xhr上传的时候执行函数
    xhr.onloadstart = function() {
        console.log('开始上传')
        //上传开始时间为当前时间
        ot = new Date().getTime(); //设置上传开始时间
        //已上传文件大小为0
        oloaded = 0; //已上传的文件大小为0
    };
    // 上传对象 addEventListener添加句柄 进度函数 当请求接收到更多数据时，周期性地触发。
    xhr.upload.addEventListener('progress', progressFunction, false);
    // 添加句柄请求完成时触发
    xhr.addEventListener("load", uploadComplete, false);
    //请求错误时候触发
    xhr.addEventListener("error", uploadFailed, false);
    //当请求被停止时候触发
    xhr.addEventListener("abort", uploadCanceled, false);
    //请求发送
    xhr.send(form, JSON.stringify({ "htmlName": htmlName }));
    showDownloadBar();
    // 进度函数周期触发
    function progressFunction(evt) {
        //设置断点
        // debugger;
        if (evt.lengthComputable) {
            //上传文件大小/总大小
            var completePercent = Math.round(evt.loaded / evt.total * 100) +
                '%';
            //进度条
            processBar.width(completePercent);
            //进度指数
            processBar.text(completePercent);
            //
            var time = $("#time");
            var nt = new Date().getTime(); //获取当前时间
            var pertime = (nt - ot) / 1000; //计算出上次调用该方法时到现在的时间差，单位为s
            ot = new Date().getTime(); //重新赋值时间，用于下次计算
            //已经上传的-上次上传的大小
            var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
            oloaded = evt.loaded; //重新赋值已上传文件大小

            //上传速度计算 上传大小差/时间差
            var speed = perload / pertime; //单位b/s
            var bspeed = speed;
            var units = 'b/s'; //单位名称
            // 如果上传速度大于1kb上传速度就用K表示并且取余
            if (speed / 1024 > 1) {
                speed = speed / 1024;
                units = 'k/s';
            }
            // 如果上传速度大于1M上传速度就用M表示
            if (speed / 1024 > 1) {
                speed = speed / 1024;
                units = 'M/s';
            }
            //精确到小数点后1位
            speed = speed.toFixed(1);
            //剩余时间=（总大小-穿传送大小）/速度精确到小数点1位
            var resttime = ((evt.total - evt.loaded) / bspeed).toFixed(1);
            //在信息后天添加信息
            $("#showInfo").html(speed + units + '<br>剩余时间：' + resttime + 's');
        }
    }
    //上传成功后回调
    function uploadComplete(evt) {
        // 隐藏上传按钮
        uploadBtn.attr('disabled', false);
        show_hear_alertByYes();
        setTimeout("outDownloadBar()", "1000");

    };
    //上传失败回调
    function uploadFailed(evt) {
        show_hear_alertByYes();
        // console.log('上传失败' + evt.target.responseText);
    }
    //终止上传
    function cancelUpload() {
        xhr.abort();
    }
    //上传取消后回调
    function uploadCanceled(evt) {
        console.log('上传取消,上传被用户取消或者浏览器断开连接:' + evt.target.responseText);
    }
    //取消按钮被点击的时候
    canelBtn.click(function() {
        //取消按钮消失
        uploadBtn.attr('disabled', false);
        //调用终止上传
        cancelUpload();
    })
}
// 文件大小转化为最常见的
function getSize(size) {
    // 文件大小速度
    var fileSize = '0KB';
    // 如果文件文件大小大于1M
    if (size > 1024 * 1024) {
        // 文件大小=大小/1M By取整 + 转化为字符串
        fileSize = (Math.round(size / (1024 * 1024))).toString() + 'MB';
    } else {
        //如果小1M 文件大小=大小/1kb 取整转化为字符串
        fileSize = (Math.round(size / 1024)).toString() + 'KB';
    }
    return fileSize;
}
// 进度条函数
function setProgress(w) {
    // 进度条的宽度改变
    processBar.width(w + '%');
}
//展示进度条
function showProgress() {
    //追踪选中元素的父元素到并显示出来
    processBar.parent().show();
}

function show_hear_alertByYes() {
    var hear_alert = document.getElementById('hear_alert');
    hear_alert.style.display = "block";
    hear_alert.className = "alert alert-success alert-dismissible"
    hear_alert.children[1].children[1].innerHTML = "上传成功"
}

function show_hear_alertByNo() {
    var hear_alert = document.getElementById('hear_alert');
    hear_alert.style.display = "block";
    hear_alert.className = "alert alert-danger alert-dismissible"
    hear_alert.children[1].children[1].innerHTML = "上传失败"
}

function showDownloadBar() {
    let download_bar = document.getElementById('download_bar');
    download_bar.style.width = "0%"
    download_bar.style.display = "block"
    let id = setInterval(fram, 30);
    let pos = 0;

    function fram() {
        if (pos == 15) {
            clearInterval(id);
            document.getElementById('cancel_button').style.display = "block"
            pos = 0;
        } else {
            pos++;
            download_bar.style.width = pos + "%";
        }
    }
}

function outDownloadBar() {
    document.getElementById('cancel_button').style.display = "none"
    let download_bar = document.getElementById('download_bar');
    let id = setInterval(fram1, 30);
    let pos = 0;

    function fram1() {
        if (pos == 15) {
            download_bar.style.display = "none";
            clearInterval(id);
            pos = 0;
        } else {
            pos++;
            download_bar.style.width = (15 - pos) + "%";
        }
    }
}