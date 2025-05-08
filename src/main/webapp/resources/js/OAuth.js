// <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}">

const getAccessToken = async (code) => {
    const response = await fetch(`${root}/api/v1/member/kakao/access-token?code=${code}`, {
        method: "GET",
    });
    const json = await response.json();
    console.log(json);
    return json.access_token;
};

const getUserInfo = async (accessToken) => {
    const response = await fetch(`${root}/api/v1/member/kakao/user-info?accessToken=${accessToken}`, {
        method: "GET",
    });
    const json = await response.json();
    return json;
};

(async () => {
    if (code) {
        const accessToken = await getAccessToken(code);
        const userInfo = await getUserInfo(accessToken);
        console.log(userInfo.id);
        console.log(userInfo.properties.nickname);
        const response = await fetch(
            `${root}/api/v1/member/kakao/login?id=${userInfo.id}&nickname=${userInfo.properties.nickname}&profileImage=${userInfo.properties.profile_image}`
        );
        const result = await response.json();

        if (result.data.redirect == "/") {
            console.log(result);
            window.location.href = result.data.redirect;
        } else if (result.data.redirect == "/member/signup") {
            const params = new URLSearchParams({
                id: result.data.id,
                name: result.data.name,
                profileImage: result.data.profileImage,
            });
            window.location.href = `${root}${result.data.redirect}?${params}`;
        }
    }
})();
