var selectedStoreName = "No store selected"
var bookicon = L.icon({
    iconUrl: '/bookstore_1.png',
    iconSize:     [70, 70],
    iconAnchor:   [19, 40],
    popupAnchor:  [0, -40]
});

    var map = L.map('map').setView([41.621, 21.690], 8);
    const attribution = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';
    const tileUrl = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
    const tiles = L.tileLayer(tileUrl, { attribution })
    tiles.addTo(map);

    let myPosition;
    function geolocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(markPosition);
        } else {
            alert("Geolocation is not supported by this browser.");
        }
    }
    function markPosition(position) {
    myPosition = L.marker([position.coords.latitude, position.coords.longitude]).addTo(map);
    getMapInfo(myPosition, selectedStore);
    }
    async function getMapInfo(start, end) {
    console.log("success map info");
    const url = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248995993511b5947b081e0eedbc69cb402&start=" + start.getLatLng().lng + "," + start.getLatLng().lat + "&end=" + end.getLatLng().lng + "," + end.getLatLng().lat;
    let info = await fetch(url);
    let data = await info.json();
    let points = data.features[0].geometry.coordinates.map(x => x.reverse());
    L.polyline(points, { color: '#b41238', opacity: 1, weight: 5 }).addTo(map);
    let distance = data.features[0].properties.summary.distance;
    let duration = data.features[0].properties.summary.duration;
    document.getElementById("information").hidden = false;
    document.getElementById("distance").innerHTML = "Растојанието од вас до книжара "+selectedStoreName+" изнесува <strong>" + (distance / 1000).toFixed(1) + " километри</strong>."
    document.getElementById("duration").innerHTML = "Времето за да стигнете до книжара "+selectedStoreName+" со автомобил изнесува <strong>" + (duration / 60).toFixed(0) + " минути</strong>."
    }

async function loadBookshops(){
        var bookId = document.getElementById("bookId").value
    console.log("Book id is " + bookId)
    const url = "/rest/bookshops/" + bookId;
    let items = await fetch(url);
    let parsed = await items.json();

    for(let item of parsed){
        let marker = L.marker([item.latitude, item.longitude], {icon: bookicon}).addTo(map).bindPopup("<h3>"+item.name+"</h3><br><a style='width: 290px' class='btn btn-outline-dark' href=/bookshops/" + item.id + ">Детален поглед</a>");
        marker.on('click', () => {
            selectedStore = marker;
            selectedStoreName = item.name;
            geolocation()
        });
    }
}
loadBookshops();
