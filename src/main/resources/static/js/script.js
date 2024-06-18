const domicilio = JSON.parse(`[
  {
    "name": "calle",
    "type": "text"
  },
  {
    "name": "numero",
    "type": "number"
  },
  {
    "name": "localidad",
    "type": "text"
  },
  {
    "name": "provincia",
    "type": "text"
  }
]`);

const paciente = JSON.parse(`[
  {
    "name": "id",
    "type": "number"
  },
  {
    "name": "nombre",
    "type": "text"
  },
  {
    "name": "apellido",
    "type": "text"
  },
  {
    "name": "dni",
    "type": "number"
  },
  {
    "name": "fechaIngreso",
    "type": "number"
  },
  {
    "name": "domicilio",
    "type": "object",
    "value": "domicilio"
  }
]`);

const odontologo = JSON.parse(`[
  {
    "name": "id",
    "type": "number"
  },
  {
    "name": "numeroMatricula",
    "type": "number"
  },
  {
    "name": "nombre",
    "type": "text"
  },
  {
    "name": "apellido",
    "type": "text"
  }
]`);

const turno = JSON.parse(`[
  {
    "name": "id",
    "type": "number"
  },
  {
    "name": "paciente",
    "type": "object",
    "value": "paciente"
  },
  {
    "name": "odontologo",
    "type": "object",
    "value": "odontologo"
  },
  {
    "name": "fechaYHora",
    "type": "dateTime"
  }
]`);

function openMenu(id) {
  const subnavContent = document.getElementById(id + '-subnav');
  subnavContent.style.display = subnavContent.style.display === '' || subnavContent.style.display === 'none' ? 'block' : 'none';
  const arrowIcon = document.getElementById(id + '-button').querySelector('.fa-caret-down')
  arrowIcon.classList.toggle('fa-rotate-180')
}

function getSearch(eventName) {
  const mainContent = document.getElementById('main-content');
  mainContent.innerHTML = `<div class="content-api">
    <label for="patientId">ID del ${capitalizeFirstLetter(eventName)}:</label>
    <input type="text" id="formId" name="formId">
    <button onclick="searchById('${eventName}')">Buscar</button>
  </div>
  <div class="listar" id="listInfo">
  </div>`
}

async function searchById(eventName) {
  const infoElement = document.getElementById('listInfo');
  const formId = document.getElementById('formId').value;
  fetch(`http://localhost:8080/${eventName}s/${formId}`)
  .then(response => response.ok?response.json(): new Error('Error en la solicitud'))
  .then( data => {
    if ( data.length === 0) {
      infoElement.innerHTML = `<h2>${capitalizeFirstLetter(eventName)} no encontrado con ID: ${formId}</h2>`;
    } else {
    infoElement.innerHTML = `
          <h2>${capitalizeFirstLetter(eventName)} encontrado:</h2>
          <p id="showElement"></p>`;
          const showElement= document.getElementById('showElement');
          let elementContent = ''
          getType(eventName).forEach(element => {
            elementContent += `<b>${element.name}:</b> ${data[element.name]}<br/>`;
          })
          showElement.innerHTML = elementContent;
    }
  });
}

function maketable(eventName,data) {
  const patientInfoElement = document.getElementById('listInfo');
  if (data.length === 0){
    patientInfoElement.innerHTML = `<h2>No hay ${capitalizeFirstLetter(eventName)}s a listar</h2>`;
  }else{
    patientInfoElement.innerHTML = `
      <table class="table">
        <thead id="listTableHead">
        </thead>
        <tbody id="listTableBody">
        </tbody>
      </table>`;
    let fileContent = '';
    const fileHead = document.createElement('tr');
    getType(eventName).forEach(e => fileContent += `<th>${capitalizeFirstLetter(e.name)}</th>`)
    fileHead.innerHTML = fileContent;
    document.getElementById('listTableHead').appendChild(fileHead);
    data.forEach(item =>{
      const fila = document.createElement('tr');
      fileContent = '';
      getType(eventName).forEach(e => fileContent += `<td>${item[e.name]}</td>`);
      fila.innerHTML = fileContent
      document.getElementById('listTableBody').appendChild(fila);
      })   
   }  
}

function getList(eventName) {
  const mainContent = document.getElementById('main-content');
  mainContent.innerHTML = `
  <div class="listar" id="listInfo">
    
  </div>`
  fetch(`http://localhost:8080/${eventName}s/listar`)
  .then(response => response.ok?response.json(): new Error('Error en la solicitud'))
  .then(data => maketable(eventName, data));
}

function getAdd(eventName){
  const mainContent = document.getElementById('main-content');
  mainContent.innerHTML = `<table class="table table-striped">
    <tbody id="tableForm">
    </tbody>
  </table>`;
  const tableForm = document.getElementById('tableForm');
  formContent = ''



  tableForm.innerHTML = formContent;
}

function getType(eventName){
  if(eventName==='odontologo') return odontologo;
  else if(eventName==='paciente') return paciente;
  else if(eventName==='turno') return turno;
  else if(eventName==='domicilio') return domicilio;
}

function capitalizeFirstLetter(string) {
  return string.length === 0 ? "" : string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}