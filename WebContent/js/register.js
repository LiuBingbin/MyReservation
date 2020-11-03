const name = document.querySelector('#name');
const pwd = document.querySelector('#pwd');
const pwd_confirm = document.querySelector('#confirm');
const tel = document.querySelector('#tel');
const mail = document.querySelector('#mail');
const register_btns = document.querySelectorAll('.register_btns button');

register_btns[0].onclick = register;
register_btns[1].onclick = reset;

async function register() {
    if(name.value.length == 0 || pwd.value.length == 0 || pwd_confirm.value.length == 0)return alert('请输入必填项！');
    if(pwd.value !== pwd_confirm)return alert('两次密码不一致！');
    let params = new URLSearchParams();
    params.append('username', name.value);
    params.append('password', pwd.value);
    params.append('tel', tel.value);
    params.append('mail', mail.value);
    const { data: res } = await axios.post('register', params);
    if (res !== 200) { alert("注册失败！"); }
    else { alert("注册成功！"); }
}

function reset() {
    name.value = '';
    pwd.value = '';
    pwd_confirm.value = '';
    tel.value = '';
    mail.value = '';
}