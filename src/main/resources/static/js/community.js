/*
    提交回复
 */
function post() {
    var contributeId = $("#contribute_id").val();
    var content = $("#comment_content").val();
    comment2target(contributeId, 1, content);
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/*
* 展开二级评论
* */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //展开二级评论
    if (comments.hasClass("in")) {
        comments.removeClass("in");
        e.classList.remove("lightskyblue");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length !== 1) {
            comments.addClass("in");
            e.classList.add("lightskyblue");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left",
                    }).append($("<img/>", {
                        "class": "media-object img-rounded img_size",
                        "src": comment.user.avatarUrl,
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body",
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name,
                    })).append($("<span/>", {
                        "html": comment.content,
                    })).append($("<span/>", {
                        "class": "pull-right show_content",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })).append($());

                    var mediaElement = $("<div/>", {
                        "class": "media margin_bottom_15",
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 ",
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
            });
            comments.addClass("in");
            e.classList.add("lightskyblue");
        }
    }
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容！");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            console.log(response);
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=a3cf6bea3750da124132&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}