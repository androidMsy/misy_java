var pageNum = 1;
var pageSize = 7;
function getData() {
    $.ajax({
        url:"/user/getAllUser",
        type:"GET",
        dataType:"json",
        data:{pageNum:pageNum,pageSize:pageSize},
        success:function (result) {
            let temp = '<caption class="table_title"><span style="font-size: 30px"><b>用户列表</b></span></caption><tr><td>编号</td><td>用户ID</td><td>用户名</td><td>真实姓名</td><td>用户头像</td><td>用户简介</td><td>操作</td></td></tr>';
            if (result.status == 0){
                let data = result.data.list;
                for (let i = 0; i < data.length;i++) {
                    let obj = data[i];
                    let userId = data[i].userId;
                    temp += '<tr class="table_item"><td>' + (i+1)
                        + '</td><td  onclick="showD(\''+userId+'\')">' + obj.userId + '</td>'
                        + '<td>' + obj.username + '</td>'
                        + '<td onclick="modifyRealname(\'' + userId + '\')">' + obj.realname + '</td>'
                        + '<td><img id="show" src = "'+obj.headerUrl+'" class="img"><div class="file"><input type="file" accept="image/*" onchange="openFile(\''+ userId +'\' , event)"></input></div></td>'
                        + '<td onclick="modifyIntro(\''+ userId +'\')">' + obj.intro + '</td>'
                        + '<td><img src="../img/delete_icon.png" onclick="deleteUser(\''+ userId +'\')" width="24px" height="24px">' +
                        '<img style="margin-left: 10px;width: 24px;height: 24px;" src="../img/chat_icon.png" onclick="toChat(\''+userId+'\')"></td></tr>'
                }
                $("#table").html(temp)
                let sss = "";
                for (let j = 0; j < result.data.navigatepageNums.length; j++){
                    sss += '<button onclick="getPageData(\'' + (j+1) + '\')">' + result.data.navigatepageNums[j] + '</button>'
                }
                sss += '<span style="margin-left: 10px;font-size: 12px" >跳转</span><input id="page_num" style="width: 20px;margin: auto 10px"><span style="font-size: 12px">页</span><button style="margin-left: 10px" onclick="getPageData(NaN)" >跳转</button>'
                $("#page_index").html(sss)
            } else {
                alert(result.msg)
            }
        },
        error:function (error) {
            console.log(error.msg)
        }
    })
}
 function showD() {
    alert("dadasdasds")
}
function deleteUser(userId) {
    if (confirm("是否删除该用户")){
        $.ajax({
            url:"/user/deleteUser",
            type: "DELETE",
            data: {userId:userId},
            success:function (result) {
                callResult(result)
            },
            error:function (error) {
                alert(error)
            }
        })
    }
}
function modifyRealname(userId) {
    let inputStr = prompt("修改真实姓名")
    if (inputStr){
        $.ajax({
            url:"/user/setRealName",
            type:"PUT",
            data: {userId:userId,realName:inputStr},
            success:function (result) {
                callResult(result)
            }
        })
    }
}
function modifyIntro(userId) {
    let inputString = prompt("修改自我简介")
    if (inputString){
        $.ajax({
            url:"/user/setIntro",
            data:{userId:userId,intro:inputString},
            type:"PUT",
            success:function (result) {
                callResult(result)
            }
        })
    }
}
function callResult(result) {
    if (result.status == 0){
        getPageData(pageNum)
    } else {
        alert(result.msg)
    }
}
function openFile(userId, ev) {
    var imgFile = ev.target.files[0];
    console.log(imgFile)
    getupToken(imgFile, userId)
}
function getupToken(file, userId) {
    $.ajax({
        url: "/getUpToken",
        type:"GET",
        success:function (token) {
            uploadImg(file, token, userId)
        }
    })

}
function uploadImg(file, token, userId) {
    let obsetver = {
        next(res){
            console.log("next" + res)
        },
        error(err){
            console.log("err" + err)
        },
        complete(res){
            let imgUrl = "http://q1z3mojjl.bkt.clouddn.com/" + res.key;
            updateHeaderImg(imgUrl, userId)
        }
    }
    let filename = (new Date()).valueOf();
    let observable = qiniu.upload(file, filename, token)
    observable.subscribe(obsetver)
}
function updateHeaderImg(imgUrl, userId) {
    $.ajax({
        url:"/user/setHeaderUrl",
        data:{headerUrl:imgUrl,userId:userId},
        type: "PUT",
        success:function (result) {
            callResult(result)
        }
    })
}
function getPageData(i) {
    if (isNaN(i)){
        pageNum = $("#page_num").val();
        if ("" == pageNum){
            return;
        }
    } else{
        pageNum = i;
    }
    getData()
}
function toChat(targetUserId) {
    let myUserId = localStorage.getItem("userId");
    if (myUserId == targetUserId){
        alert("不能与自己聊天")
        return;
    }
    window.location.href = "/th/chat?targetUserId=" + targetUserId
}
