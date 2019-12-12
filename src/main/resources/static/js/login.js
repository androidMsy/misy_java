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
    var data = {username:username,password:password}
    data = JSON.stringify(data)
    $.ajax({
        url: "/user/login",
        type: "POST",
        data: data,
        contentType:"application/json;charset=UTF-8",
        dataType: "json",
        success:function (response) {
            console.log("response:" + response)
            if (response.status == 0){
                var userAccount = response.data
                var token = userAccount.token
                document.cookie = token
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

