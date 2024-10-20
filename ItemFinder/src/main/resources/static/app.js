
//const API_BASE_URL = 'http://80.211.200.112:8080/api'


document.addEventListener('DOMContentLoaded', () => {
    const lostItemsContainer = document.getElementById('lost-items-container');
    const foundItemsContainer = document.getElementById('found-items-container');
    const lostForm = document.getElementById('lost-form');
    const foundForm = document.getElementById('found-form');
    const searchInput = document.getElementById('search-input');
    const toggleLostButton = document.getElementById('toggle-lost');
    const toggleFoundButton = document.getElementById('toggle-found');
    const addItemTitle = document.getElementById('add-item-title');

    const API_BASE_URL = 'http://localhost:8080/api';
    let currentType = 'lost'; // Track current type (lost or found)

    // Function to fetch lost items from the server
    const fetchLostItems = async () => {
        const response = await fetch(`${API_BASE_URL}/lost-items`);
        const lostItems = await response.json();
        renderLostItems(lostItems);
    };

    // Function to fetch found items from the server
    const fetchFoundItems = async () => {
        const response = await fetch(`${API_BASE_URL}/found-items`);
        const foundItems = await response.json();
        renderFoundItems(foundItems);
    };

    // Function to render lost items
    const renderLostItems = (items) => {
        lostItemsContainer.innerHTML = '';
        items.forEach(item => {
            const itemDiv = document.createElement('div');
            itemDiv.classList.add('item-tile');
            itemDiv.innerHTML = `<h4>${item.name}</h4>
                                 <p>Date: ${item.lostDate}</p>
                                 <p>Description: ${item.description || 'N/A'}</p>
                                 <p>Image: ${item.imagePath || 'N/A'}</p>
                                 <p>Status: ${item.status || 'N/A'}</p>
                                 <p>Losing Location: ${item.losingLocation || 'N/A'}</p>`;
            lostItemsContainer.appendChild(itemDiv);
        });
    };

    // Function to render found items
    const renderFoundItems = (items) => {
        foundItemsContainer.innerHTML = '';
        items.forEach(item => {
            const itemDiv = document.createElement('div');
            itemDiv.classList.add('item-tile');
            itemDiv.innerHTML = `<h4>${item.name}</h4>
                                 <p>Date: ${item.date}</p>
                                 <p>Description: ${item.description || 'N/A'}</p>
                                 <p>Image: ${item.imagePath || 'N/A'}</p>
                                 <p>Status: ${item.status || 'N/A'}</p>
                                 <p>Finding Location: ${item.findingLocation || 'N/A'}</p>`;
            foundItemsContainer.appendChild(itemDiv);
        });
    };

    // Handle form submission for lost items
    lostForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(lostForm);
        const lostItem = {
            name: formData.get('name'),
            lostDate: formData.get('date'),
            description: formData.get('description'), // New field
            imagePath: formData.get('imagePath'), // New field
            status: formData.get('status'), // New field
            losingLocation: formData.get('losingLocation'), // New field
            // Add other fields if needed
        };

        const response = await fetch(`${API_BASE_URL}/lost-items`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(lostItem),
        });

        if (response.ok) {
            await fetchLostItems();
            lostForm.reset();
        } else {
            console.error('Failed to add lost item:', response.statusText);
        }
    });

    // Handle form submission for found items
    foundForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(foundForm);
        const foundItem = {
            name: formData.get('name'),
            date: formData.get('date'),
            description: formData.get('description'), // New field
            imagePath: formData.get('imagePath'), // New field
            status: formData.get('status'), // New field
            findingLocation: formData.get('findingLocation'), // New field
            // Add other fields if needed
        };

        const response = await fetch(`${API_BASE_URL}/found-items`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(foundItem),
        });

        if (response.ok) {
            await fetchFoundItems();
            foundForm.reset();
        } else {
            console.error('Failed to add found item:', response.statusText);
        }
    });

    // Search functionality for both lost and found items
    searchInput.addEventListener('input', async () => {
        const searchTerm = searchInput.value.toLowerCase();
        let items = [];

        if (currentType === 'lost') {
            const response = await fetch(`${API_BASE_URL}/lost-items`);
            items = await response.json();
        } else {
            const response = await fetch(`${API_BASE_URL}/found-items`);
            items = await response.json();
        }

        const filteredItems = items.filter(item =>
            item.name.toLowerCase().includes(searchTerm) ||
            item.date.toLowerCase().includes(searchTerm)
        );

        if (currentType === 'lost') {
            renderLostItems(filteredItems);
        } else {
            renderFoundItems(filteredItems);
        }
    });

    // Toggle between lost and found items
    toggleLostButton.addEventListener('click', () => {
        currentType = 'lost';
        addItemTitle.innerText = 'Add Lost Item';
        lostForm.style.display = 'block';
        foundForm.style.display = 'none';
        lostItemsContainer.style.display = 'block';
        foundItemsContainer.style.display = 'none';
        fetchLostItems(); // Fetch and render lost items
    });

    toggleFoundButton.addEventListener('click', () => {
        currentType = 'found';
        addItemTitle.innerText = 'Add Found Item';
        lostForm.style.display = 'none';
        foundForm.style.display = 'block';
        lostItemsContainer.style.display = 'none';
        foundItemsContainer.style.display = 'block';
        fetchFoundItems(); // Fetch and render found items
    });

    // Initial fetch of lost items
    fetchLostItems();
});
