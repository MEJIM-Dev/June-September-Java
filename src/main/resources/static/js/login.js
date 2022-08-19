const email = document.getElementById("username")
const password = document.getElementById("password")
const submit = document.getElementById("submit")

console.log(
                "email:",  email.value,
                "password:", password.value)

submit.addEventListener("click",function(e){
    e.preventDefault();

    if(email.value!="" && password.value!="" ){
        fetch("http://localhost:8080/login",{
            method:"POST",
            headers: {
                        'Content-type': 'application/json; charset=UTF-8'
                   },
            body: JSON.stringify({
                email  : email.value,
                password: password.value
            })
        })
        .then((res)=>{
            if(res.status==200){
              alert("user login successful")
               return

            }
            alert("Failed to login")
        })
        .catch((e)=>{
            alert(e)
        })
        return
    }

    return alert("form not completed yet")

})