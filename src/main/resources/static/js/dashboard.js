const submit = document.getElementById("logout")

submit.addEventListener("click", function(e){
        fetch("http://localhost:8080/logout")
        .then((response)=>{
            if(response.status==204 || response.status==200){
                alert("Successfully logout")
                window.location.href="http://localhost:8080/login"
            } else{
                alert("not logged out yet")
            }
        })
        .catch((error)=>{
            console.log(error)
            alert("Couldn't connect to server 500 Status code")
        })
})