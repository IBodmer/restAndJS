$(async function () {
    await getCustomers();
    await infoUser();
    // await tittle();
    // await getNewUserForm();
    // await getDefaultModal();
    // await createUser();

})

const userFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllCustomers: async () => await fetch('http://localhost:8080/admin'),
    findUserByUsername: async () => await fetch(`http://localhost:8080/admin/principal`),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    addNewUser: async (user) => await fetch('api/users', {
        method: 'POST',
        headers: userFetch.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (user, id) => await fetch(`api/users/${id}`, {
        method: 'PUT',
        headers: userFetch.head,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`api/users/${id}`, {method: 'DELETE', headers: userFetch.head})
}

async function getCustomers() {
    let temp = '';
    let info = document.querySelector('#tableForListCustomers');
    await userFetch.findAllCustomers()
        .then(res => res.json())
        .then(list => {
            list.forEach(
                customer => {
                    console.log(customer.roles)
                    temp += `
                    <tr>
                        <th scope="row">${customer.id}</th>
                        <td>${customer.firstname}</td>
                        <td>${customer.lastname}</td>
                        <td>${customer.email}</td>
                        <td>${customer.age}</td>
                        <td>${customer.roles.map(r => r.role.substring(5))}</td>
                        <td> </td>
                        
                    
            `;
                }
            )
        });
    info.innerHTML = temp;

}

async function infoUser() {
    let temp = '';
    let info = document.querySelector('#letstry');
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(customer => {
            temp += `
             <div class="container-fluid">
                <a class="navbar-brand" style="color: aliceblue">${customer.email}</a>
                    <form class="d-flex">
                        <a href="/logout"
                           class="btn btn-primary btn-lg active"
                           role="button"
                           aria-pressed="true"
                            >Logout</a>
                    </form>
             </div>
            `;
        });
    info.innerHTML = temp;
}

