var auth2 = gapi.auth2.getAuthInstance();
function signOut() {
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
}