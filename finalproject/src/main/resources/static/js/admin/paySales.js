window.addEventListener('DOMContentLoaded', event => {

        const ctx = document.getElementById('myChart');
        const table = document.getElementById('memberTable');
        const rows = table.querySelectorAll('tbody tr');
        const quarterSums = {
            '01': 0,
            '02': 0,
            '03': 0,
            '04': 0
        };

        rows.forEach(row => {
            const payDate = row.querySelector('td:nth-child(6)').textContent;
            const month = payDate.split('-')[1];
            const payMoney = parseInt(row.querySelector('td:nth-child(5) span').textContent.replace(/[^0-9]/g, ''), 10);

            if (quarterSums.hasOwnProperty(month)) {
                quarterSums[month] += payMoney;
            }
        });

        const dataValues = Object.values(quarterSums);

  
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: ['1분기', '2분기', '3분기', '4분기'],
      datasets: [{
        label: '단위 : 원',
        data: dataValues,
        /*backgroundColor: [
	      'rgb(255, 99, 132)',
	      'rgb(54, 162, 235)',
	      'rgb(54, 162, 235)',
	      'rgb(255, 205, 86)'
	    ],*/
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true,
          max: 100000, // y축 최대값 설정
          ticks: {
            stepSize: 10000,
            callback: function (value) {
              return value.toLocaleString();
            }
          }
        }
      }
    }
  });
});