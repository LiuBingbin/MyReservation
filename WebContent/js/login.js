const name_input = document.querySelector('.name_input');
const pwd_input = document.querySelector('.pwd_input');
const login_btns = document.querySelectorAll('.login_btns button');
const radio = document.getElementsByName('roles');

login_btns[0].onclick = login;
login_btns[1].onclick = reset;
document.onkeydown = function (e) {
    if (e.key === 'Enter') {
        login();
    }
}

async function login() {
    let flag = radio[0].checked ? 'user' : 'admin';
    let params = new URLSearchParams();
    params.append('username', name_input.value);
    params.append('password', pwd_input.value);
    params.append('flag', flag);
    let { data: res } = await axios.post('login', params);
    if (res !== 200) { return alert("用户名或密码错误"); }
    else {
        if(flag === 'user'){
            window.location = './index.html';
        }else{
            window.location = './admin.html';
        }
        document.cookie = 'username=' + name_input.value;
    }
}

function reset() {
    name_input.value = '';
    pwd_input.value = '';
}