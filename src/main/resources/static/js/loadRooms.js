function getQueryParam(param) {
    var searchParams = new URLSearchParams(window.location.search);
    return searchParams.get(param);
}

function displayRooms(rooms) {
    const roomsList = document.getElementById('hotels-row');
    roomsList.innerHTML = '';

    rooms.forEach(room => {
        const roomElement = `
                <div class="col-md-4">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h5 class="card-title">${room.type} Room</h5>
                            <p class="card-text">Guests: ${room.guestsCount}</p>
                            <p class="card-text">Number: ${room.number}</p>
                            <p class="card-text">Price per night: $${room.pricePerNight.toFixed(2)}</p>
                            <p class="card-text">Available: ${room.availability ? 'Yes' : 'No'}</p>
                            <a class="btn btn-primary" href="/reservation?id=${room.id}">Reserve Now</a>
                        </div>
                    </div>
                </div>
            `;
        roomsList.innerHTML += roomElement;
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const hotelId = getQueryParam('id');
    if (!hotelId) {
        console.error('Hotel ID is not specified in the query parameters.');
        return;
    }

    fetch(`http://localhost:8080/hotels/${hotelId}`)
        .then(response => response.json())
        .then(hotelData => {
            document.title = hotelData.name + ' - StayEz';

            const hotelsContainer = document.getElementById('hotels-container');
            if (!hotelsContainer) {
                console.error('Hotel container element not found.');
                return;
            }

            const heading = hotelsContainer.querySelector('h2');
            if (heading) {
                heading.textContent = 'Rooms at ' + hotelData.name;
            } else {
                console.error('Heading element not found.');
                return;
            }

            displayRooms(hotelData.rooms);
        })
        .catch(error => {
            console.error('Error fetching hotel data:', error);
        });
});
