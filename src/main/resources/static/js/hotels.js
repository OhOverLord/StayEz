function createHotelCard(hotel) {
    return `
        <div class="col-lg-4 col-md-6 d-flex align-items-stretch">
            <div class="card mb-4 flex-fill">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${hotel.name}</h5>
                    <p class="card-text">${hotel.description}</p>
                    <ul class="list-unstyled mt-auto">
                        <li>Address: ${hotel.address}</li>
                        <li>Stars: <span class="stars"> ${'★'.repeat(hotel.stars)} </span></li>
                        <li>Country: ${hotel.country}</li>
                        <li>City: ${hotel.city}</li>
                    </ul>
                    <a href="hotel?id=${hotel.id}" class="btn btn-primary mt-2">View Rooms</a>
                </div>
            </div>
        </div>
    `;
}

function loadAndDisplayHotels() {
fetch('http://localhost:8080/hotels')
    .then(response => response.json())
    .then(hotels => {
        const hotelsRow = document.getElementById('hotels-row');
        hotelsRow.innerHTML = '';
        hotels.forEach(hotel => {
            hotelsRow.innerHTML += createHotelCard(hotel);
        });
    })
    .catch(error => {
        console.error('Error fetching hotels:', error);
    });
}

document.addEventListener('DOMContentLoaded', loadAndDisplayHotels);
