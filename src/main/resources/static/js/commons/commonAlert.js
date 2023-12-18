async function errorAlert(message) {
    await Swal.fire({
        icon: "error",
        title: message,
        showConfirmButton: false,
        timer: 1500
    })
}