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
    let marker1 = L.marker([42.0043012, 21.4083577], {icon: bookicon}).addTo(map);
    marker1.on('click', () => {
    selectedStore = marker1;
    selectedStoreName = "Матица 1"
    geolocation();
});
    let marker2 = L.marker([41.7369521, 22.1911505], {icon: bookicon}).addTo(map);
    marker2.on('click', () => {
    selectedStore = marker2;
    selectedStoreName = "Матица 2"
    geolocation();
});
    let marker3 = L.marker([41.0230063, 21.3189164], {icon: bookicon}).addTo(map);
    marker3.on('click', () => {
    selectedStore = marker3;
    selectedStoreName = "Матица 3"
    geolocation();
});
    let marker4 = L.marker([41.0221417, 21.3310960], {icon: bookicon}).addTo(map);
    marker4.on('click', () => {
    selectedStore = marker4;
    selectedStoreName = "Литература 1"
    geolocation();
});
    let marker5 = L.marker([41.9931370, 20.9623640], {icon: bookicon}).addTo(map);
    marker5.on('click', () => {
    selectedStore = marker5;
    selectedStoreName = "Издавачки центар ТРИ 1"
    geolocation();
});
    let marker6 = L.marker([41.7367383, 22.2034127], {icon: bookicon}).addTo(map);
    marker6.on('click', () => {
    selectedStore = marker6;
    selectedStoreName = "ИКОНА 1"
    geolocation();
});
    let marker7 = L.marker([41.3693879, 21.5145619], {icon: bookicon}).addTo(map);
    marker7.on('click', () => {
    selectedStore = marker7;
    selectedStoreName = "Култура 1"
    geolocation();
});
    let marker8 = L.marker([41.1078977, 20.8087351], {icon: bookicon}).addTo(map);
    marker8.on('click', () => {
    selectedStore = marker8;
    selectedStoreName = "Македоника Литера 1"
    geolocation();
});

    let marker9 = L.marker([41.9929199,21.4740172], {icon: bookicon}).addTo(map);
    marker9.on('click', () => {
        selectedStore = marker9;
        selectedStoreName = "Мартина 1"
        geolocation();
    });

    let marker10 = L.marker([41.9930174,21.4098921], {icon: bookicon}).addTo(map);
    marker10.on('click', () => {
        selectedStore = marker10;
        selectedStoreName = "Просветно Дело АД Скопје 1"
        geolocation();
    });

    let marker11 = L.marker([42.0022287,21.4583354], {icon: bookicon}).addTo(map);
    marker11.on('click', () => {
        selectedStore = marker11;
        selectedStoreName = "Сакам книги 1"
        geolocation();
    });

    let marker12 = L.marker([41.9885200,21.4250843], {icon: bookicon}).addTo(map);
    marker12.on('click', () => {
        selectedStore = marker12;
        selectedStoreName = "Топер 1"
        geolocation();
    });

    let marker13 = L.marker([42.0016016,21.4518169], {icon: bookicon}).addTo(map);
    marker13.on('click', () => {
        selectedStore = marker13;
        selectedStoreName = "Топер 2"
        geolocation();
    });

    let marker14 = L.marker([41.7598776,22.1659847], {icon: bookicon}).addTo(map);
    marker14.on('click', () => {
        selectedStore = marker14;
        selectedStoreName = "Тримакс 1"
        geolocation();
    });


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
