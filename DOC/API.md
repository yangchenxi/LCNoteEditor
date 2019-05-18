# API:

### Github:

**Base API URL:**

``https://api.github.com``

#### 1. Oauth2.0 URL:

```
https://github.com/login/oauth/authorize?client_id=d9daf1e6ea82bb4149f6&redirect_uri=lcnote%3A%2F%2FOauthResp&scope=public_repo&response_type=token&response_mode=form_post
```

**Client ID:** d9daf1e6ea82bb4149f6

**Client Secret:** 8c698898b46019d8d448a8472e515bcdf720565e

**Redirect Back URI:**
```
lcnote://oauthresp?code=db3f371888d2276911d8
```

**Code will be expired in 10 min**

#### 2. Get Access Token:

```
POST /login/oauth/access_token?client_id=d9daf1e6ea82bb4149f6&amp; client_secret=8c698898b46019d8d448a8472e515bcdf720565e&amp; code=b107faf21ab4a0e3cef5 HTTP/1.1
Host: github.com
Accept:  application/json
cache-control: no-cache
```

JsonCallBack:

```json
{
    "access_token": "26aaeac6ef8694f123dc1c125c1377cfdf4d5eb7",
    "token_type": "bearer",
    "scope": "public_repo"
}
```

### 3.List User Repo:

```
GET /user/repos HTTP/1.1
Host: api.github.com
Authorization: token 26aaeac6ef8694f123dc1c125c1377cfdf4d5eb7
cache-control: no-cache
```

**[Response](./lsRepoCallBack.json)**

### 4.Upload File to Repo:

```
PUT /repos/yangchenxi/GithubApiTest/contents/[folderpath]/tes3t4.png HTTP/1.1
Host: api.github.com
Authorization: token 26aaeac6ef8694f123dc1c125c1377cfdf4d5eb7
cache-control: no-cache
{
  "message": "my commit message",
  "committer": {
    "name": "LeetCodeEditorAndroid",
    "email": "xxx@live.cn"
  },
  "content": "image de base64"
 }
```

**[Response](./upFileResponse.json)**

**Image URL:** Response.content.download_url


### LeetCode:

**Base URL:** `https://leetcode.com/`

#### Github Oauth2.0 URL:
```
https://github.com/login/oauth/authorize?client_id=6efe458dfe2230acceea&redirect_uri=https%3A%2F%2Fleetcode.com%2Faccounts%2Fgithub%2Flogin%2Fcallback%2F&scope=user%3Aemail&response_type=code
```
**In this app, I'm using Android CookieManager and github cookie, WebView to Do a fake Oauth2.0 **

#### Get Cookie:

``https://leetcode.com/accounts/github/login/callback/?code=a5f7e01164eff7de1cba``

#### Get All Problems And User Status:

```
GET /api/problems/all/? HTTP/1.1
Host: leetcode.com
Accept: application/json, text/javascript, */*; q=0.01
Cookie: Cookie: XXXXX
cache-control: no-cache
```

**[Response](./LCInfo.json)**

#### Get Note Content Given A Question Name:

Note: you can find the CSRF Token in Cookies and titleSlug in LCInfo.json

```
POST /graphql HTTP/1.1
Host: leetcode.com
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:66.0) Gecko/20100101 Firefox/66.0
Cookie:XXXX
x-csrftoken: paWGAJxf0ccY2IlExyH8zeYcNxTHfdUkaq4XB4KOLxote5YDGwzCPYZMJetvwJsI
content-type: application/json
cache-control: no-cache
{"operationName":"QuestionNote","variables":{"titleSlug":"integer-to-roman"},"query":"query QuestionNote($titleSlug: String!) {\n  question(titleSlug: $titleSlug) {\n    questionId\n    note\n    __typename\n  }\n}\n"}
```

Response:

```json
{
    "data": {
        "question": {
            "questionId": "12",
            "note": "hahahahahhahdf",
            "__typename": "QuestionNode"
        }
    }
}
```

#### UpdateNote:

```
POST /graphql HTTP/1.1
Host: leetcode.com
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:66.0) Gecko/20100101 Firefox/66.0
Cookie: XXX
x-csrftoken: paWGAJxf0ccY2IlExyH8zeYcNxTHfdUkaq4XB4KOLxote5YDGwzCPYZMJetvwJsI
content-type: application/json
Referer: https://leetcode.com/problems/integer-to-roman/
cache-control: no-cache
{"operationName":"updateNote","variables":{"titleSlug":"integer-to-roman","content":"hahahahahhahdf\n\n<img src=\"https://github.com/yangchenxi/BiliMusicPlayer/blob/master/art/playstore.png?raw=truesdfsdfsdf\" width=\"256\" alt=\"get it on googlePlaytore\">\n![](http://)d"},"query":"mutation updateNote($titleSlug: String!, $content: String!) {\n  updateNote(titleSlug: $titleSlug, content: $content) {\n    ok\n    error\n    question {\n      questionId\n      note\n      __typename\n    }\n    __typename\n  }\n}\n"}


```

Response:

```json
{
    "data": {
        "updateNote": {
            "ok": true,
            "error": "",
            "question": {
                "questionId": "12",
                "note": "hahahahahhahdf\n\n<img src=\"https://github.com/yangchenxi/BiliMusicPlayer/blob/master/art/playstore.png?raw=true\" width=\"256\" alt=\"get it on googlePlaytore\">\n![](http://)d",
                "__typename": "QuestionNode"
            },
            "__typename": "UpdateNote"
        }
    }
}
```
