function login() {
    var  username = $.trim($("#username").val())
    var password = document.getElementById("password").value
    if ("" == username){
        alert("请输入用户名")
        return
    }
    if ("" == password){
        alert("请输入密码")
        return
    }
    let data = {username:username,password:password}
    data = JSON.stringify(data)
    $.ajax({
        url: "/user/login",
        type: "POST",
        data: data,
        contentType:"application/json;charset=UTF-8",
        success:function (response) {
            console.log("response:" + response)
            if (response.status == 0){
                let userAccount = response.data
                let token = userAccount.token
                document.cookie = token
                localStorage.setItem("username", userAccount.username)
                localStorage.setItem("realname", userAccount.realname)
                localStorage.setItem("userId", userAccount.userId)
                localStorage.setItem("headerUrl", userAccount.headerUrl)
                localStorage.setItem("intro", userAccount.intro)
                window.location.href = "/th/home"
            }else {
                alert(response.msg)
            }
        },
        error:function (error) {
            console.log("error111:" + error)
        },
    });
}

