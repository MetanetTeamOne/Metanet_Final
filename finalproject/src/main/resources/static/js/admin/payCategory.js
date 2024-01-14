window.addEventListener('DOMContentLoaded', event => {

  const ctx = document.getElementById('myCharts');
  const table = document.getElementById('memberTable');
  const rows = table.querySelectorAll('tbody tr');
  const methods = {
    '직접결제': 0,
    '자동결제': 0,
  };

  rows.forEach(row => {
    const data = row.querySelector('td:nth-child(3)').textContent;

    if (methods.hasOwnProperty(data)) {
      methods[data] += 1;
    }
  });

  const dataValues = Object.values(methods); // 수정된 부분: quarterSums 대신 methods를 사용

  new Chart(ctx, {
    type: 'doughnut',
    data: {
	  labels: [
	    '직접결제',
	    '구독결제',
	  ],
	  datasets: [{
	    label: '# 단위 : 건',
	    data: dataValues,
	    backgroundColor: [
	      'rgb(255, 99, 132)',
	      'rgb(54, 162, 235)',
	    ],
	    hoverOffset: 4
	  }]
	},
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
});