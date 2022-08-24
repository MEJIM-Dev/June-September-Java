const firstname = document.getElementById("firstname")
const lastname = document.getElementById("lastname")
const email = document.getElementById("username")
const age = document.getElementById("age")
const gender = document.getElementById("gender")
const password = document.getElementById("password")
const submit = document.getElementById("submit")

console.log("firstname:", firstname.value,
                "lastname:", lastname.value,
                "email:",  email.value,
                "password:", password.value,
                "age:", age.value,
                "gender:", gender.value)

submit.addEventListener("click",function(e){
    e.preventDefault();

    if(firstname.value!="" && lastname.value!="" && email.value!="" && gender.value!="" && age.value!="" && password.value!="" ){
        fetch("http://localhost:8080/register",{
            method:"POST",
            headers: {
                        'Content-type': 'application/json; charset=UTF-8'
                   },
            body: JSON.stringify({
                firstName: firstname.value,
                lastname: lastname.value,
                email  : email.value,
                password: password.value,
                age: age.value,
                gender: gender.value,
            })
        })
        .then((res)=>{
            if(res.status==200){
              alert("user created successfully")
               return window.location.href="/login"
            }
            alert("Failed to save user")
        })
        .catch((e)=>{
            alert(e)
        })
        return
    }

    return alert("form not completed yet")

})