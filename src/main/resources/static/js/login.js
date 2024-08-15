document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('loginForm');

    if (form) { // 폼이 존재할 때만 이벤트 리스너를 추가
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // 기본 폼 제출 동작 방지

            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            // API 요청
            fetch('/api/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
                credentials: 'include'
            })
                .then(response => {
                    if (response.ok) {
                        // 로그인 성공
                        window.location.href = '/user/success'; // 로그인 성공 후 페이지로 리디렉션
                    } else {
                        // 로그인 실패
                        alert('로그인 정보가 일치하지 않습니다.'); // 실패 메시지 표시
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred');
                });
        });
    } else {
        console.error('Login form not found');
    }
});
