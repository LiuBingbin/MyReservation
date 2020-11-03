
judgeLogin();

const userSpan = document.querySelector('.head_box .head_right .user');
const foodBox = document.querySelector('section>.food_box');
const gotop = document.querySelector("nav .gotop");
const foodCard = document.querySelector(".food_card_container");
const numTips = document.querySelector(".nav_right .num_tips");
const cartBtn = document.querySelector(".nav_right .cart span:first-child");
const cartBox = document.querySelector(".nav_right .cart_box");
const total = cartBox.querySelector(".total span");
const clearBtn = cartBox.querySelector(".clear");
const submitBtn = cartBox.querySelector(".cart_submit button");
const logoutBtn = document.querySelector(".message .logout");
const balance = document.querySelector(".message .balance span:first-child");
const moneyBox = document.querySelector(".money_container");
const moneyCloseBtn = moneyBox.querySelector(".close");
const rechargeBtn = moneyBox.querySelector(".recharge button");
const rechargeInput = moneyBox.querySelector(".recharge input");
const ubalance = moneyBox.querySelector(".balance span");
const suggestBtn = document.querySelector(".message .suggestion");
const suggestBox = document.querySelector(".suggest_container");
const suggestCloseBtn = suggestBox.querySelector(".close");
const noticesBtn = document.querySelector(".message .notice");
const noticesBox = document.querySelector(".notices_container");
const noticesCloseBtn = noticesBox.querySelector(".close");
const queue = document.querySelector(".message .queue span");

let uname = document.cookie.slice('username='.length);
userSpan.innerHTML = uname;



showFood();

showBalance();

showQueue();

cart();

window.onscroll = scrollFunction;

rechargeBtn.onclick = recharge;

balance.onclick = function() {
    moneyBox.style.display = 'flex'
};
moneyCloseBtn.onclick =function() {
    moneyBox.style.display = 'none'
}
suggestBtn.onclick = function() {
    suggestBox.style.display = 'flex'
}
suggestCloseBtn.onclick =function() {
    suggestBox.style.display = 'none'
}
noticesBtn.onclick = function() {
    noticesBox.style.display = 'flex'
}
noticesCloseBtn.onclick =function() {
    noticesBox.style.display = 'none'
}

gotop.onclick = topFunction;

foodBox.onclick = showFoodCard;

cartBtn.onclick = toggleCart;

clearBtn.onclick = clearCart;

submitBtn.onclick = submitCart;

logoutBtn.onclick = logout;

//获取用户余额
async function showBalance() {
    let { data:res } = await axios.get('searchUser?name='+uname);
    balance.innerHTML = '¥'+res.balance;
    ubalance.innerHTML = '余额：¥'+res.balance;
    return res.balance;
}
//改变余额
async function changeBalance(bal) {
    let params = new URLSearchParams();
    params.append('balance', bal);
    params.append('name', uname);
    await axios.post('changeBalance',params);
}
//充值
async function recharge() {
    let value = parseFloat(rechargeInput.value);
    if(!value)return;
    let res = parseFloat(await showBalance());
    let bal = res+value;
    changeBalance(bal);
    showBalance();
    alert("充值成功！");
    location.reload();
}
//判断登录状态
function judgeLogin() {
    if(!document.cookie){
        alert('您还未登录！');
        window.location = './login.html';
        return;
    }
}
//退出登录
function logout() {
    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
    alert('已退出登录');
    judgeLogin();
    clearCart();
}
//根据id搜索菜品信息
async function findById(id) {
    let { data: res } = await axios.get('searchFood?id=' + id);
    return res;

}
//显示菜品详情
async function showFoodCard(e) {
    if (!e.target.closest('li')) return;
    let id = e.target.closest('li').getAttribute('id');
    let res = await findById(id);
    let num = sessionStorage.getItem(id + 'num') || 0;
    let str = `
        <div class="food_card">
            <img src="./img/`+ res.image + `">
            <div class="title">`+ res.name + `</div>
            <span class="price">¥`+ res.price + `</span>
            <span class="num">/1份</span>
            <del>¥`+ res.oprice + `</del>
            <p class="sales">月售`+ res.sales + `</p>
            <p class="detail">`+ res.detail + `</p>
            <div class="counter">
                <button>-</button>
                <span>`+ num + `</span>
                <button>+</button>
            </div>
            <div class="close">×</div>
        </div>
       `
    foodCard.innerHTML = str;
    foodCard.style.display = 'flex'

    const closeBtn = document.querySelector(".food_card_container .close");
    const counterBtns = document.querySelectorAll(".food_card_container button");

    counterBtns[0].onclick = function () {
        subNum();
        calcTotal();
        refreshTips();
    }
    counterBtns[1].onclick = function () {
        addNum();
        calcTotal();
        refreshTips();
    }
    closeBtn.onclick = close;


    function subNum() {
        if (num <= 0) return;
        if (num == 1) {
            delCart(id);
        }
        num--;
        refreshNum(id, num);
    }
    function addNum() {
        if (num <= 0) {
            createCart(res);
        }
        num++;
        refreshNum(id, num);
    }



}

//创建购物车条目
function createCart(res) {
    let cartItem = document.createElement('li');
    let str = `
                    <img src="./img/`+ res.image + `">
                    <span class="title">`+ res.name + `</span>
                    <span class="price">¥`+ res.price + `</span>
                    <div class="cart_counter">
                        <span>-</span> 
                        <span class="cart_num">`+ sessionStorage.getItem(res.id + 'num') + `</span> 
                        <span>+</span>
                    </div>
                    `
    cartItem.setAttribute('id', res.id);
    cartItem.className = "cart_item";
    cartItem.innerHTML = str;
    cartBox.querySelector('ul').appendChild(cartItem);
}
//遍历会话内容创建购物车
async function cart() {
    let sessionKeys = Object.keys(sessionStorage);
    for (var i = 0; i < sessionKeys.length; i++) {
        
        let res = await findById(parseInt(sessionKeys[i]))
        createCart(res);
    }
    calcTotal();
    refreshTips();
}
//删除购物车条目
function delCart(id) {
    cartBox.querySelector('li[id="' + id + '"]').remove();
}

//更新数量
function refreshNum(id, num) {

    if (!num == 0) {
        sessionStorage.setItem(id + 'num', num);
    } else {
        sessionStorage.removeItem(id + 'num');
    }

    document.querySelector('.counter span').innerHTML = num;
    if (cartBox.querySelector('li[id="' + id + '"]'))
        cartBox.querySelector('li[id="' + id + '"] .cart_counter .cart_num').innerHTML = num;
}

//计算总价
async function calcTotal() {
    let cartLis = cartBox.querySelectorAll('li');
    let sum = 0;

    for (var i = 0; i < cartLis.length; i++) {
        let res = await findById(cartLis[i].id);
        sum += res.price * +sessionStorage.getItem(cartLis[i].id + 'num');
    }
    total.innerHTML = '¥' + sum;
}
//清空购物车
function clearCart() {
    let cartLis = cartBox.querySelectorAll('li');
    for (var i = 0; i < cartLis.length; i++) {
        sessionStorage.removeItem(cartLis[i].id + 'num');
        cartLis[i].remove();
    }
    total.innerHTML = '¥0';
    refreshTips();
    cartBox.style.display = 'none';
}
//更新小红点
function refreshTips() {
    let cartLis = cartBox.querySelectorAll('li');
    let sum = 0;

    for (var i = 0; i < cartLis.length; i++) {
        sum += parseInt(sessionStorage.getItem(cartLis[i].id + 'num'));
    }
    numTips.innerHTML = sum;
}

//从后端获取数据展示菜品
async function showFood() {
    let { data: res } = await axios.get('showFood');
    res.forEach(item => {
        createFoodLi(item);
    });

}

//动态创建每个菜品
function createFoodLi(item) {
    let str = `
        <div class="food_item">
            <img src="./img/`+ item.image + `">
            <div class="title">`+ item.name + `</div>
            <p class="sales">月售`+ item.sales + `</p>
            <span class="price">¥`+ item.price + `</span>
            <span class="num">/1份</span>
            <del>¥`+ item.oprice + `</del>
        </div>
        `
    let foodLi = document.createElement('li');
    foodLi.setAttribute('id', item.id)
    foodLi.innerHTML = str;
    foodBox.appendChild(foodLi);
    // sessionStorage.setItem(item.id+"num" ,0);
}

function close() {
    foodCard.style.display = 'none';
}

//监听滚动条事件
function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        gotop.style.visibility = "visible";
    } else {
        gotop.style.visibility = "hidden";
    }
}
//回到顶部方法
function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

//显示隐藏购物车
function toggleCart() {
    cartBox.style.display = cartBox.style.display === 'block' ? 'none' : 'block';
}
//去结算
function submitCart() {
    if(!cartBox.querySelectorAll('li').length == 0)
    window.location = './order.html';
}
//查询取餐号
async function showQueue() {
    let { data:res } = await axios.get('searchOrder?uname='+uname);
    if(res[res.length-1])
    queue.innerHTML = res[res.length-1].queue;
}