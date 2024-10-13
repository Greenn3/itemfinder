// app.js

document.addEventListener('DOMContentLoaded', function () {
    showTab('lost'); // Show lost items by default

    // Handle Lost Item form submission
    document.getElementById('lostForm').addEventListener('submit', function (e) {
        e.preventDefault();
        addLostItem();
    });

    // Handle Found Item form submission
    document.getElementById('foundForm').addEventListener('submit', function (e) {
        e.preventDefault();
        addFoundItem();
    });

    // Load lost and found items on page load
    loadLostItems();
    loadFoundItems();
});

// Function to show the selected tab
function showTab(tabName) {
    // Hide all tabs
    document.querySelectorAll('.tab').forEach(tab => {
        tab.style.display = 'none';
    });

    // Remove active class from all nav links
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });

    // Show the selected tab
    document.getElementById(tabName).style.display = 'block';
    // Add active class to the corresponding nav link
    document.querySelector(`.nav-link[onclick="showTab('${tabName}')"]`).classList.add('active');
}

// Function to load Lost Items
function loadLostItems() {
    fetch('/api/lost-items')
        .then(response => response.json())
        .then(data => {
            displayItems(data, 'lostItemsList');
        })
        .catch(error => console.error('Error fetching lost items:', error));
}

// Function to load Found Items
function loadFoundItems() {
    fetch('/api/found-items')
        .then(response => response.json())
        .then(data => {
            displayItems(data, 'foundItemsList');
        })
        .catch(error => console.error('Error fetching found items:', error));
}

// Function to display items as cards
function displayItems(items, containerId) {
    const container = document.getElementById(containerId);
    container.innerHTML = ''; // Clear existing items

    if (items.length === 0) {
        container.innerHTML = '<p class="text-muted">No items found.</p>';
        return;
    }

    items.forEach(item => {
        const col = document.createElement('div');
        col.className = 'col-md-4';

        const card = document.createElement('div');
        card.className = 'card item-card';

        // Optional: Display image if available
        if (item.imageUrl) {
            const img = document.createElement('img');
            img.src = item.imageUrl; // Ensure this URL is correctly served by the backend
            img.className = 'card-img-top';
            img.alt = item.name;
            card.appendChild(img);
        }

        const cardBody = document.createElement('div');
        cardBody.className = 'card-body';

        const title = document.createElement('h5');
        title.className = 'card-title';
        title.textContent = item.name;

        const date = document.createElement('p');
        date.className = 'card-text';
        date.textContent = `Date: ${item.date}`;

        // Optional: Description
        if (item.description) {
            const desc = document.createElement('p');
            desc.className = 'card-text';
            desc.textContent = item.description;
            cardBody.appendChild(desc);
        }

        cardBody.appendChild(title);
        cardBody.appendChild(date);

        card.appendChild(cardBody);
        col.appendChild(card);
        container.appendChild(col);
    });
}

// Function to add Lost Item
function addLostItem() {
    const name = document.getElementById('lostName').value.trim();
    const date = document.getElementById('lostDate').value;
    const description = document.getElementById('lostDescription').value.trim();
    const imageInput = document.getElementById('lostImage');

    // Prepare form data for image upload if implemented
    const formData = new FormData();
    formData.append('name', name);
    formData.append('date', date);
    formData.append('description', description);

    fetch('/api/lost-items', {
        method: 'POST',
        // For image uploads, use multipart/form-data
        // headers: { 'Content-Type': 'application/json' }, // Remove this header
        body: formData
    })
        .then(response => response.json())
        .then(item => {
            loadLostItems(); // Refresh the list
            document.getElementById('lostForm').reset(); // Reset the form
        })
        .catch(error => console.error('Error adding lost item:', error));
}

// Function to add Found Item
function addFoundItem() {
    const name = document.getElementById('foundName').value.trim();
    const date = document.getElementById('foundDate').value;
    const description = document.getElementById('foundDescription').value.trim();
    const imageInput = document.getElementById('foundImage');

    // Prepare form data for image upload if implemented
    const formData = new FormData();
    formData.append('name', name);
    formData.append('date', date);
    formData.append('description', description);


    fetch('/api/found-items', {
        method: 'POST',
        // headers: { 'Content-Type': 'application/json' }, // Remove this header
        body: formData
    })
        .then(response => response.json())
        .then(item => {
            loadFoundItems(); // Refresh the list
            document.getElementById('foundForm').reset(); // Reset the form
        })
        .catch(error => console.error('Error adding found item:', error));
}

// Function to search items (both Lost and Found)
function searchItems() {
    const name = document.getElementById('searchName').value.trim();
    const date = document.getElementById('searchDate').value;

    // Fetch Lost Items
    fetch(`/api/lost-items/search?name=${encodeURIComponent(name)}&date=${encodeURIComponent(date)}`)
        .then(response => response.json())
        .then(data => {
            displayItems(data, 'lostItemsList');
        })
        .catch(error => console.error('Error searching lost items:', error));

    // Fetch Found Items
    fetch(`/api/found-items/search?name=${encodeURIComponent(name)}&date=${encodeURIComponent(date)}`)
        .then(response => response.json())
        .then(data => {
            displayItems(data, 'foundItemsList');
        })
        .catch(error => console.error('Error searching found items:', error));
}
