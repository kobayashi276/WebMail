var $ = document.querySelector.bind(document);

var username = $("#username");
var email = $("#email");
var password = $("#password");
var comfirmPassword = $("#comfirm-password");
var formSubmit = $("form");

function showError(input , message){
    let parent = input.parentElement;
    let error = parent.querySelector('small');
    parent.classList.add("error");
    error.innerText = message;
    input.value ='';
}

function showSuccess(input){
    let parent = input.parentElement;
    let error = parent.querySelector("small");
    parent.classList.remove("error");
    error.innerText = "";
}
function checkEmptyError(listInput){
    let isEmpty = false ;
    listInput.forEach(function(input){
        input.value = input.value.trim();
        if(!input.value){
            isEmpty = true
            showError(input,"Khong duoc de trong truong nay");
        }
        else {
            showSuccess(input);
        }
    })
    return isEmpty;
}
function checkEmailError (input){
    const regexEmail = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    input.value = input.value.trim();
    let isEmail = false;
    if(regexEmail.test(input.value)){
         showSuccess(input);
    }
    else {
        isEmail = true;
        showError(input, "Email Invalid");
    }
    return isEmail;

}
function checkLegnthError(input , min ,max){
    input.value = input.value.trim();
    let isLength = false;
    if(input.value.length < min || input.value.length > max){
        isLength = true;
        showError(input, `Password co it nhat ${min} and toi da ${max} character`);
    }
    else {
        showSuccess(input);
    }
    return isLength;
}
function checkComfirmError(input1, input2){
    input1.value = input1.value.trim();
    input2.value = input2.value.trim();
    let isComfirm =false;
    if(input1.value == input2.value){
        showSuccess(input2);
    }
    else {
        isComfirm = true;
        showError(input2,"sai mat khau");
    }
    return isComfirm;
}


formSubmit.addEventListener("submit", (e) => {
    e.preventDefault();
    // let checkEmail = checkEmailError(email);
    // let checkEmpty = checkEmptyError([username , email , password , comfirmPassword])
    // let checkLength = checkLegnthError(password, 6 , 20);
    let checkComfirm = checkComfirmError(password,comfirmPassword);
    if(checkComfirm){
        // call API
    }
    else {
        //do some thing
    }
});