async function editCustomer(modal, id) {
    let oneUser = await userFetch.findOneUser(id);
    let user = oneUser.json();

    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-info" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(customer => {
        let bodyForm = `
            <form class="form-group text-center" id="editUser">
               <div class="form-group">
                    <label for="CustomerId" class="col-form-label">ID</label>
                    <input type="text" class="form-control username" id="CustomerId" value="${customer.id}" readonly>
               </div>
                   
               <div class="form-group">
                    <label for="firstname" class="col-form-label">First Name</label>
                    <input type="text" class="form-control username" id="firstname" value="${customer.firstname}">
               </div>

                <div class="form-group">
                    <label for="lastname" class="com-form-label">Last Name</label>
                    <input type="text" class="form-control" id="lastname" value="${customer.lastname}">
                </div>

           
                <div class="form-group">
                    <label for="age" class="com-form-label">Age</label>
                    <input type="number" class="form-control" id="age" value="${customer.age}">
                </div>

                <div class="form-group">
                    <label for="email" class="com-form-label">Email</label>
                    <input type="text" class="form-control" id="email" value="${customer.email}">
                </div>
                
                <div class="form-group">
                    <label for="roles" class="com-form-label">Role</label>
                    <select multiple id="roles" size="2" class="form-control" style="max-height: 100px">
                    <option value="ROLE_USER">USER</option>
                    <option value="ROLE_ADMIN">ADMIN</option>
                    </select>
                </div>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#editButton").on('click', async () => {
        let checkedRoles = () => {
            let array = []
            let options = document.querySelector('#roles').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    array.push(roleList[i])
                }
            }
            return array;
        }
        let customerID = modal.find("#CustomerId").val().trim();
        let firstname = modal.find("#firstname").val().trim();
        let lastname = modal.find("#lastname").val().trim();
        let age = modal.find("#age").val().trim();
        let email = modal.find("#email").val().trim();
        let data = {
            id: customerID,
            firstname: firstname,
            lastname: lastname,
            age: age,
            email: email,
            roles: checkedRoles()

        }
        const response = await userFetch.updateUser(data, id);

        if (response.ok) {
            await getCustomers();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}