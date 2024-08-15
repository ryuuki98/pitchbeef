// static/js/validationJoin.js

// 사용자 이름 중복 체크 함수
function checkUsername() {
    const username = document.getElementById("username").value;
    const errorElement = document.getElementById("usernameError");
    const submitButton = document.querySelector("form button[type='submit']");

    if (username === '') {
        errorElement.style.display = "none"; // 사용자 이름이 비어있을 때 오류 메시지 숨김
        submitButton.disabled = false; // 사용자 이름 필드가 비어있을 때 버튼 활성화
        return;
    }

    // AJAX 요청 생성
    fetch(`/api/user/${username}`)
        .then(response => {
            if (response.status === 400) {
                // 중복된 경우
                errorElement.style.display = "inline"; // 오류 메시지 표시
                submitButton.disabled = true; // submit 버튼 비활성화
            } else {
                // 중복되지 않은 경우
                errorElement.style.display = "none"; // 오류 메시지 숨김
                submitButton.disabled = false; // submit 버튼 활성화
            }
        })
        .catch(error => {
            console.error("Error checking username:", error);
            submitButton.disabled = false; // 오류 발생 시 기본적으로 버튼 활성화
        });
}

// 폼 제출 시 빈 필드 검증 및 API 요청 처리
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(event) {
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const email = document.getElementById("email").value;
        const errorElement = document.getElementById("usernameError");
        const submitButton = document.querySelector("form button[type='submit']");

        // 빈 필드 검증
        if (!username || !password || !email) {
            alert('모든 필드를 채워주세요.');
            event.preventDefault(); // 폼 제출 방지
            return; // 검증 실패 시 추가 작업을 방지
        }

        // 사용자 이름 중복 체크를 위해 AJAX 요청을 보냅니다.
        if (submitButton.disabled) {
            event.preventDefault(); // 폼 제출 방지
            return;
        }

        // 폼 데이터 수집
        const formData = {
            username: username,
            password: password,
            email: email
        };

        // API 요청 생성
        fetch('/api/user/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/user/login'; // 성공 시 리디렉션
                } else {
                    return response.text().then(errorMsg => {
                        alert('서버 오류: ' + errorMsg);
                    });
                }
            })
            .catch(error => {
                console.error("Error submitting form:", error);
                alert('서버 오류가 발생했습니다.');
            });

        event.preventDefault(); // 폼 제출 방지
    });
});
