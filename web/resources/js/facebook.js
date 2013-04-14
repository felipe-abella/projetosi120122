window.fbAsyncInit = function() {
    FB.init({
        appId: '470600623013872',
        channelUrl: '//localhost:38544/projetosi120122/faces/facebook/channel.html',
        status: true,
        cookie: true,
        xfbml: true
    });
};

function fblogged(response, form) {
    var access_token = response.authResponse.accessToken;

    FB.api('/me', function(response2) {
        form["faceForm:authToken"].value = access_token;
        form["faceForm:faceID"].value = response2.id;
        form["faceForm:faceName"].value = response2.name;
        form["faceForm:faceEmail"].value = response2.email;
        form.submit();
    });
}

function fblogin(form) {
    FB.login(function(response) {
        if (response.authResponse) {
            fblogged(response, form);
        } else {
            alert('Login pelo Facebook falhou.');
        }
    }, {scope: 'email'});
}

(function(d) {
    var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
    if (d.getElementById(id)) {
        return;
    }
    js = d.createElement('script');
    js.id = id;
    js.async = true;
    js.src = "//connect.facebook.net/en_US/all.js";
    ref.parentNode.insertBefore(js, ref);
}(document));
