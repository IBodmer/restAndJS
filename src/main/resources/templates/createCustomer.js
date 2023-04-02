async function createCustomer() {
    $('#addUser').click(async () =>  {
        let addCustomerForm = $('#addForm')
        let firstname = addCustomerForm.find('#firstnameCreate').val().trim();
        let lastname = addCustomerForm.find('#lastnameCreate').val().trim();
        let age = addCustomerForm.find('#ageCreate').val().trim();
        let email = addCustomerForm.find('#emailCreate').val().trim();
        let checkedRoles = () => {
            let array = []
            let options = document.querySelector('#rolesCreate').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    array.push(roleList[i])
                }
            }
            return array;
        }
        let data = {
            "firstname": firstname,
            "lastname": lastname,
            "age": age,
            "email": email,
            "roles": checkedRoles()
        }

        const response = await userFetch.addNewUser(data);
        if (response.ok) {
            await getCustomers();
            addCustomerForm.find('#firstnameCreate').val('');
            addCustomerForm.find('#lastnameCreate').val('');
            addCustomerForm.find('#ageCreate').val('');
            addCustomerForm.find('#emailCreate').val('');
            addCustomerForm.find(checkedRoles()).val('');
            let alert = `<div class="alert alert-success alert-dismissible fade show col-12" role="alert" id="successMessage">
                         User create successful!
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addCustomerForm.prepend(alert);
            $('.nav-tabs a[href="#adminTable"]').tab('show');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addCustomerForm.prepend(alert);
        }
    });
}