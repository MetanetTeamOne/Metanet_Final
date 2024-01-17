$().ready(function() {
    $('#btnDelete').on('click', function() {
        swal(swTitle, swComment, swImage)
        .then((result) => {
            if (result) {
                document.getElementById('sendForm').submit();
            }
        });
    });
});


$().ready(function() {
    $('#btnInsert').on('click', function() {
        swal(swTitle, swComment, swImage)
        .then((result) => {
            if (result) {
                document.getElementById('sendForm').submit();
            }
        });
    });
});

$().ready(function() {
    $('#btnUpdate').on('click', function() {
        document.getElementById('sendForm').submit();
    });
});