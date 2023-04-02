let roleList = [
    {id: 1, role: "ROLE_USER"},
    {id: 2, role: "ROLE_ADMIN"}
]
let isUser = true;

$(async function () {
    await getCustomer();
    await infoCustomer();
    await tittle();
    await getCustomers();
    await getNewCustomerForm();
    await getDefaultModal();
    await createCustomer();

})

const userFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('http://localhost:8080/api/v1/users'),
    findUserByUsername: async () => await fetch(`http://localhost:8080/api/v1/user`),
    findOneUser: async (id) => await fetch(`http://localhost:8080/api/v1/user/${id}`),
    addNewUser: async (user) => await fetch('http://localhost:8080/api/v1/new', {
        method: 'POST',
        headers: userFetch.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (user, id) => await fetch(`http://localhost:8080/api/v1/user/update/${id}`, {
        method: 'PATCH',
        headers: userFetch.head,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`http://localhost:8080/api/v1/user/delete/${id}`, {
        method: 'DELETE',
        headers: userFetch.head
    })
}

async function infoCustomer() {
    let temp = '';
    const info = document.querySelector('#info');
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(user => {
            temp += `
             <span style="color: white">
               ${user.username} with roles <span>${user.roles.map(e => " " + e.role.substring(5))}</span>
                </div>
            </span>
                </tr>
            `;
        });
    info.innerHTML = temp;
}