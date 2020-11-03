const table = document.querySelector('table');
const total = document.querySelector('.order_submit .total span');
const submitBtn = document.querySelector('.order_submit .submit button');
let uname = document.cookie.slice('username='.length);
let sessionKeys = Object.keys(sessionStorage);
let sum = 0;

if(!localStorage.getItem('queue_num') || localStorage.getItem('queue_num')>999){
    localStorage.setItem('queue_num',0)
}
order();
submitBtn.onclick = submitOrder;

//展示订单
async function order() {
    for (var i = 0; i < sessionKeys.length; i++) {
        let res = await findById(parseInt(sessionKeys[i]))
        createOrderTr(res);
        sum += res.price*sessionStorage.getItem(res.id+'num');
    }
    total.innerHTML = '¥'+sum;
}
//创建订单条目
function createOrderTr(res) {
    let orderTrs = document.createElement('tr');
    let str = `
            <td><img src="./img/`+res.image+`"></td>
            <td>`+res.name+`</td>
            <td><p>`+res.detail+`</p></td>
            <td>`+res.price+`</td>
            <td>`+sessionStorage.getItem(res.id+'num')+`</td>
            <td>`+res.price*sessionStorage.getItem(res.id+'num')+`</td>
         `
    orderTrs.innerHTML = str;
    table.appendChild(orderTrs);
}

//根据id搜索菜品信息
async function findById(id) {
    let { data: res } = await axios.get('searchFood?id=' + id);
    return res;

}

//提交订单
async function submitOrder() {  
    let balance = parseFloat(await showBalance());
    let sessionKeys = Object.keys(sessionStorage);
    let uname = document.cookie.slice("username=".length);
    let date = new Date();
    let dateStr = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
    let queue = parseInt(localStorage.getItem('queue_num'))+1;
    for (var i = 0; i < sessionKeys.length; i++) {
        let id = parseInt(sessionKeys[i]);
        let params = new URLSearchParams();
        params.append('uname', uname);
        params.append('foodid', id);
        params.append('foodnum', sessionStorage.getItem(id+'num'));
        params.append('fooddate', dateStr);
        params.append('queue', queue);
        params.append('total', sum);
        const { data: res } = await axios.post('addOrder', params);
        if (res !== 200) { alert("提交失败！"); return}
        sessionStorage.removeItem(id+'num')
    }

    localStorage.setItem('queue_num',queue);
    changeBalance(balance-sum);
    alert('提交成功！您的取餐号为：'+queue);
    window.location = './index.html';
}
//获取用户余额
async function showBalance() {
    let { data:res } = await axios.get('searchUser?name='+uname);
    return res.balance;
}
//改变余额
async function changeBalance(bal) {
    let params = new URLSearchParams();
    params.append('balance', bal);
    params.append('name', uname);
    let { data:res } = await axios.post('changeBalance',params);
    console.log(res);
}