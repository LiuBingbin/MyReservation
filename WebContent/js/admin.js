const navLis = document.querySelectorAll(".nav_left li");
const table = document.querySelector(".box table")

for (let i = 0; i < navLis.length; i++) {
    navLis[i].addEventListener('click', function () {
        for (let j = 0; j < navLis.length; j++) {
            navLis[j].className = '';
        }
        this.className = 'active';
    })
}
navLis[0].addEventListener('click', showUser);
navLis[1].addEventListener('click', showFood);
navLis[2].addEventListener('click', showOrder);

showUser();

async function showUser() {
    let thStr = `<tr>
                    <th>id</th>
                    <th>用户名</th>
                    <th>手机</th>
                    <th>邮箱</th>
                    <th>余额</th>
                    <th>操作</th>
                </tr>`
    table.innerHTML = thStr;
    let { data: res } = await axios.get('showUser');
    res.forEach(item => {
        let str = `
                    <td>`+ item.id + `</td>
                    <td>`+ item.name + `</td>
                    <td>`+ item.tel + `</td>
                    <td>`+ item.mail + `</td>
                    <td>`+ item.balance + `</td>
                    <td><a href="javascript:;">修改</a>/
                    <a href="javascript:;" onclick="delUser(`+ item.id + `)">删除</a></td>
                 `
        createTr(str, item);
        let flag = true;
        table.querySelector('tr[id="' + item.id + '"] td:last-child').onclick = () => {
            changeUser(item.id, item.name, item.tel, item.mail, item.balance,flag);
            flag = !flag;
        }
    });

}

async function showFood() {
    let thStr = `<tr>
                    <th>id</th>
                    <th>菜名</th>
                    <th>单价</th>
                    <th>详情</th>
                    <th>原价</th>
                    <th>月销量</th>
                    <th>操作</th>
                </tr>`
    table.innerHTML = thStr;
    let { data: res } = await axios.get('showFood');
    res.forEach(item => {
        let str = `
                    <td>`+ item.id + `</td>
                    <td>`+ item.name + `</td>
                    <td>`+ item.price + `</td>
                    <td><p>`+ item.detail + `</p></td>
                    <td>`+ item.oprice + `</td>
                    <td>`+ item.sales + `</td>
                    <td><a href="javascript:;">修改</a>/
                    <a href="javascript:;" onclick="delFood(`+ item.id + `)">删除</a></td>
                 `
        createTr(str, item);
        let flag = true;
        table.querySelector('tr[id="' + item.id + '"] td:last-child').onclick = () => {
            changeFood(item.id, item.name, item.price, item.detail, item.oprice,item.sales,flag);
            flag = !flag;
        }
    });
}

async function showOrder() {
    let thStr = `<tr>
                    <th>id</th>
                    <th>用户名</th>
                    <th>菜品</th>
                    <th>数量</th>
                    <th>订单总价</th>
                    <th>时间</th>
                    <th>操作</th>
                </tr>`
    table.innerHTML = thStr;
    let { data: res } = await axios.get('showOrder');
    res.forEach(async function (item, index) {
        let food = await findById(item.foodid);
        let str = `
                    <td>`+ item.queue + `</td>
                    <td>`+ item.uname + `</td>
                    <td>`+ food.name + `</td>
                    <td>`+ item.foodnum + `</td>
                    <td>`+ item.total + `</td>
                    <td><p>`+ item.fooddate + `</p></td>
                    <td><a href="javascript:;" onclick="delOrder(`+ item.queue + `)">删除</a></td>
                 `
        createTr(str, item);
    });
}






function createTr(str, item) {
    let tr = document.createElement('tr');
    tr.innerHTML = str;
    tr.setAttribute('id', item.id);
    table.appendChild(tr);
}

async function findById(id) {
    let { data: res } = await axios.get('searchFood?id=' + id);
    return res;

}

async function delUser(id) {
    let params = new URLSearchParams();
    params.append('id', id);
    let { data: res } = await axios.post('deleteUser', params);
    if (res == 200) { alert('删除成功！'); navLis[0].click(); }
    else alert('删除失败！');
}
async function delFood(id) {
    let params = new URLSearchParams();
    params.append('id', id);
    let { data: res } = await axios.post('deleteFood', params);
    if (res == 200) { alert('删除成功！'); navLis[1].click(); }
    else alert('删除失败！');
}
async function delOrder(queue) {
    let params = new URLSearchParams();
    params.append('queue', queue);
    let { data: res } = await axios.post('deleteOrder', params);
    if (res == 200) { alert('删除成功！'); navLis[2].click(); }
    else alert('删除失败！');
}

async function changeUser(id, name, tel, mail, balance,flag) {
    let tr = table.querySelector('tr[id="' + id + '"]');
    let nameTd = tr.querySelector('td:nth-child(2)');
    let telTd = tr.querySelector('td:nth-child(3)');
    let mailTd = tr.querySelector('td:nth-child(4)');
    let balanceTd = tr.querySelector('td:nth-child(5)');
    if (flag == true) {
        
        nameTd.innerHTML = `<input type="text" value="`+ name + `">`;
        telTd.innerHTML = `<input type="text" value="`+ tel + `">`;
        mailTd.innerHTML = `<input type="text" value="`+ mail + `">`;
        balanceTd.innerHTML = `<input type="text" value="`+ balance + `">`;
    } else {
        let params = new URLSearchParams();
        params.append('id', id);
        params.append('name', nameTd.querySelector('input').value);
        params.append('tel', telTd.querySelector('input').value);
        params.append('mail', mailTd.querySelector('input').value);
        params.append('balance', balanceTd.querySelector('input').value);

        let { data: res } = await axios.post('changeUser', params);
        if (res == 200) { alert('修改成功！'); navLis[0].click(); }
        else alert('修改失败！');
    }

}

async function changeFood(id, name, price, detail, oprice,sales,flag) {
    let tr = table.querySelector('tr[id="' + id + '"]');
    let nameTd = tr.querySelector('td:nth-child(2)');
    let priceTd = tr.querySelector('td:nth-child(3)');
    let detailTd = tr.querySelector('td:nth-child(4)');
    let opriceTd = tr.querySelector('td:nth-child(5)');
    let salesTd = tr.querySelector('td:nth-child(6)');
    if (flag == true) {
        
        nameTd.innerHTML = `<input type="text" value="`+ name + `">`;
        priceTd.innerHTML = `<input type="text" value="`+ price + `">`;
        detailTd.innerHTML = `<input type="text" value="`+ detail + `">`;
        opriceTd.innerHTML = `<input type="text" value="`+ oprice + `">`;
        salesTd.innerHTML = `<input type="text" value="`+ sales + `">`;
    } else {
        let params = new URLSearchParams();
        params.append('id', id);
        params.append('name', nameTd.querySelector('input').value);
        params.append('price', priceTd.querySelector('input').value);
        params.append('detail', detailTd.querySelector('input').value);
        params.append('oprice', opriceTd.querySelector('input').value);
        params.append('sales', salesTd.querySelector('input').value);

        let { data: res } = await axios.post('changeFood', params);
        if (res == 200) { alert('修改成功！'); navLis[1].click(); }
        else alert('修改失败！');
    }

}
