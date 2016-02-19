var a;

function main() {
    a = gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().id_token;
    console.log(a);
}
