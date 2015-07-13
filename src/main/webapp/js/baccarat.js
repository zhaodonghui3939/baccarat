/**
 * Created by root on 15-7-9.
 */
var initUrl = 'service/baccarat/init',
    gameId;
function initGame() {
    $.ajax({
        url: initUrl,
        type: 'GET',
        success: initGameOK
    });
}

function initGameOK(res) {
    gameId = res.data;
    alert(gameId);
}

initGame();