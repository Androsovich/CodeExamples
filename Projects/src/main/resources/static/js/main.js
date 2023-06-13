$(document).ready(function () {
        const defaultStatus = 'INACTIVE';

        $("#locales").change(function () {
            var selectedOption = $('#locales').val();
            if (selectedOption !== '') {
                if (window.location.pathname === '/j_spring_security_check') {
                    window.location.replace(window.location.origin + '/api/user-info?lang=' + selectedOption);
                } else {
                    window.location.replace('?lang=' + selectedOption);
                }
            }
        });

        $('#signup').on('click', function () {
            $('#modal').show();
        });

        $('#add_comment').on('click', function () {
            $('#modal').show();
        });

        $(document).on('click', '#modal_close', function () {
            $('#modal').hide();
            $('#modal_add_user_to_project').hide();
        });

        $('#view_tasks').click(function () {
            let e = document.getElementById("projects");
            let projectId = e.value;

            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: '/api/projects/' + projectId,
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);

                    /*<![CDATA[*/

                    $('#tasks').html(data);

                    /*]]>*/
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });

        $('#save_comment').click(function () {
            let search = {};
            search["content"] = $("#content").val();
            search["taskId"] = $("#task_id").attr('data-id');

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/api/comment/save",
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    setTimeout(function () {
                        window.location.reload();
                    }, 100);

                    $('#modal').hide();
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                    $('#modal').hide();
                    alert(" Bad Request");
                }
            });
        });

        $('#save_user').click(function () {
            let search = {};
            let role = $("#roles").val();
            search["firstName"] = $("#firstName").val();
            search["lastName"] = $("#lastName").val();
            search["middleName"] = $("#middleName").val();
            search["userName"] = $("#user_login").val();
            search["password"] = $("#user_password").val();
            search["birthday"] = $("#birthday").val();
            search["email"] = $("#user_email").val();
            search["role"] = Array.of(role.toString());

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/signup",
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    $('#modal').hide();
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                    $('#modal').hide();
                    alert(" Cannot save user ");
                }
            });
        });

        $('#view_users_project').on('click', function () {
            let search = {};
            search["id"] = $("#task_id").attr('data-id');

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/api/users/view/project",
                data: JSON.stringify(search),
                dataType: 'html',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);

                    $('#list_users').html(data);

                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });

        $(document).on('click', '#change_user', function () {
            let search = {};
            search["id"] = $("#task_id").attr('data-id');
            search["userId"] = $('#users_ option:selected').val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/api/task/update",
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);

                    $('#list_users').hide();

                    window.location.reload();
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });


        $('#statuses').on('change', function () {
            $('#change_status').show();
        });

        $('#change_status').click(function () {
            let search = {};
            search["id"] = $("#task_id").attr('data-id');
            search["status"] = $('#statuses option:selected').val();
            if (search["status"] === 'COMPLETED') {
                search['endDate'] = new Date();
            }


            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/api/task/update/status",
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    $('#statuses').blur();

                    window.location.reload();

                    $('#modal').hide();
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                    $('#change_status').hide();
                }
            });
        });

        $('#update_project').click(function () {
            let search = {};
            search["id"] = $("#update_project").attr('data-project-id');
            search["title"] = $("#title").val();
            search["status"] = $('#statuses option:selected').val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/security/project/update",
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    setTimeout(function () {
                        window.location.href = "/security/projects"
                    }, 100);
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                    $('#change_status').hide();
                }
            });
        });

        $('#update_task_admin').click(function () {
            let search = {};
            search["id"] = $("#update_task_admin").attr('data-task-id');
            search["title"] = $("#title").val();
            search["startDate"] = $("#startDate").val();
            search["status"] = $('#statuses option:selected').val();
            search["userId"] = $('#users_ option:selected').val();
            if (search["status"] === 'COMPLETED') {
                search['endDate'] = new Date();
            }

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/api/task/update",
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    setTimeout(function () {
                        window.location.href = "/security/projects"
                    }, 100);
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });


        $('.delete_project').click(function (event) {
            event.preventDefault();
            let search = {};
            search["id"] = $(this).attr('data-project-id');

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: $(this).attr('href'),
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    window.location.reload();

                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });


        $('.delete_task').click(function (event) {
            event.preventDefault();
            let search = {};
            search["id"] = $(this).attr('data-task-id');

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: $(this).attr('href'),
                data: JSON.stringify(search),
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    window.location.reload();

                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });

        $('.delete_user_project').click(function (event) {
            event.preventDefault();

            let user_id = $(this).attr('data-user-id');
            let project_id = $(this).attr('data-project_id');
            let result_data = 'project_id=' + project_id + '&user_id=' + user_id;

            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded",
                url: $(this).attr('href'),
                data: result_data,
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    window.location.reload();

                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });


        $('.adding_user_project').click(function (event) {
            event.preventDefault();
            let project_id = $(this).attr('data-project_id');
            let result_data = 'project_id=' + project_id;

            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded",
                url: $(this).attr('href'),
                data: result_data,
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    $('#add_user_to_project').html(data);
                    $('#modal_add_user_to_project').show();

                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });

        $(document).on('click', '#save_project_user', function (event) {
            event.preventDefault();

            let user_id = $('#add_p_user option:selected').val();
            let project_id = $('#save_project_user').attr('data-project-id');
            let result_data = 'project_id=' + project_id + '&user_id=' + user_id;

            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded",
                url: "/security/project/save/user",
                data: result_data,
                dataType: 'text',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    window.location.reload();
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        });
        $(document).on('click', '#create_task', function (event) {
            event.preventDefault();
            let project_id = $(this).attr('data-project_id');

            let search = {};
            search["title"] = $("#title").val();
            search["startDate"] = new Date();
            search["status"] = defaultStatus;
            search["projectId"] = project_id;
            search["userId"] = $('#users_ option:selected').val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/security/task/save/",
                data: JSON.stringify(search),
                dataType: 'html',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    location.href = "/security/projects";
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            })
        });

        $(document).on('click', '#create_project', function (event) {
            event.preventDefault();

            let search = {};
            search["title"] = $("#title").val();
            search["status"] = defaultStatus;

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/security/project/save",
                data: JSON.stringify(search),
                dataType: 'html',
                cache: false,
                success: function (data) {
                    console.log("SUCCESS : ", data);
                    location.href = "/security/projects";
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            })
        });
    }
)
