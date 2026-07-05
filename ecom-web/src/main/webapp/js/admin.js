function previewImage(input, previewId) {
    const preview = document.getElementById(previewId);
    if (input.files && input.files[0] && preview) {
        const reader = new FileReader();
        reader.onload = function(e) { preview.src = e.target.result; }
        reader.readAsDataURL(input.files[0]);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const editButtons = document.querySelectorAll('.edit-btn');

    const imageBasePath = CONTEXT_PATH + "/images/";

    editButtons.forEach(button => {
        button.addEventListener('click', function(e) {

            const btn = e.currentTarget;

            try {

                document.getElementById('edit-id').value = btn.getAttribute('data-id') || "";
                document.getElementById('edit-name').value = btn.getAttribute('data-name') || "";
                document.getElementById('edit-desc').value = btn.getAttribute('data-desc') || "";
                document.getElementById('edit-price').value = btn.getAttribute('data-price') || "";
                document.getElementById('edit-stock').value = btn.getAttribute('data-stock') || "";

                document.getElementById('edit-category').value = btn.getAttribute('data-category') || "";
                document.getElementById('edit-brand').value = btn.getAttribute('data-brand') || "";
                document.getElementById('edit-color').value = btn.getAttribute('data-color') || "";
                document.getElementById('edit-storage').value = btn.getAttribute('data-storage') || "";

                for (let i = 1; i <= 4; i++) {
                    const imgElement = document.getElementById('edit-prev' + i);
                    const fileName = btn.getAttribute('data-img' + i);

                    if (imgElement) {
                        if (fileName && fileName.trim() !== "" && fileName !== "null") {
                            imgElement.src = imageBasePath + fileName;
                        } else {
                            imgElement.src = "https://placehold.co/400x400/f8f9fa/a3a3a3?text=No+Image";
                        }
                    }
                }
            } catch (error) {
                console.error("Mapping Error: ", error);
            }
        });
    });
});