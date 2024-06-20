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
    "type": "number",
    "ignore": true
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
    "type": "date"
  },
  {
    "name": "domicilio",
    "type": "object",
    "list": false,
    "value": "domicilio"
  }
]`);

const odontologo = JSON.parse(`[
  {
    "name": "id",
    "type": "number",
    "ignore": true
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
    "type": "number",
    "ignore": true
  },
  {
    "name": "paciente",
    "type": "object",
    "list": true
  },
  {
    "name": "odontologo",
    "type": "object",
    "list": true
  },
  {
    "name": "fechaYHora",
    "type": "dateTime-local"
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
        if (element.ignore) return;
        if (element.type === 'object' && !element.list) {
          elementContent += `<b>${element.name}</b><br/>`;
          let subData=data[element.name]
          console.log(data)
          console.log(subData)
          getType(element.name).forEach(subElement =>{
          elementContent += `<b>${subElement.name}:</b> ${subData[subElement.name]}<br/>`;
            })
        }else{
          if(element.list){
            let subData=data[element.name]
            option.textContent = subElement.nombre + " " +subElement.apellido;
            elementContent += `<b>${element.name}:</b> ${data[element.name]}<br/>`;
          }else{
            elementContent += `<b>${element.name}:</b> ${data[element.name]}<br/>`;
          }  
        }
      })
      if(data.nombre){
        elementContent += getButtons(eventName,data.name,data.id);
      }
      if(data.fechaYHora){
        elementContent +=  getButtons(eventName,data.fechaYHora,data.id);
      }
      showElement.innerHTML = elementContent;
    }
  });
}

function getButtons(eventName,name,id){
  return `<button class="actionButton" onclick="edit('${eventName}','${name}','${id}')">
  <i class="fa-solid fa-pencil"></i><br>Editar</button>
  <button class="actionButton" onclick="drop('${eventName}','${name}','${id}')">
  <i class="fa-solid fa-eraser"></i><br>Borrar</button>`;
}

function drop(eventName,name,id){
  const userConfirmed = confirm(`¿Está seguro que desea borrar el ${eventName} ${name}?`);
  if(userConfirmed){
    fetch(`http://localhost:8080/${eventName}s/${id}`,{
      method: 'DELETE'
  }).then(response => document.getElementById('main-content').innerHTML = `<H3>${eventName} con id ${id} Eliminado</h3>`);
  }
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
    fileContent += `<th>actions</th>`
    fileHead.innerHTML = fileContent;
    document.getElementById('listTableHead').appendChild(fileHead);
    data.forEach(item =>{
      const fila = document.createElement('tr');
      fileContent = '';
      getType(eventName).forEach(e => {
        if(e.list){
          fileContent += `<td>${item[e.name].nombre} ${item[e.name].apellido}</td>`;
        }else{
          fileContent += `<td>${item[e.name]}</td>`
        }
        });
      fila.innerHTML = fileContent
      const buttons = document.createElement('tr');
      if(item.nombre){
        buttons.innerHTML = getButtons(eventName,item.nombre,item.id);
      }
      if(item.fechaYHora){
        buttons.innerHTML = getButtons(eventName,item.fechaYHora,data.id);
      }
      fila.appendChild(buttons)
      document.getElementById('listTableBody').appendChild(fila);
      })   
   }  
}

function getList(eventName) {
  const mainContent = document.getElementById('main-content');
  mainContent.innerHTML = `
  <div class="listar" id="listInfo">
    
  </div>`
  getListInfo(eventName).then(data => maketable(eventName, data));
}

function getListInfo(eventName){
  return  fetch(`http://localhost:8080/${eventName}s/listar`)
  .then(response => response.ok?response.json(): new Error('Error en la solicitud'))
}

function getAdd(eventName){
  const mainContent = document.getElementById('main-content');
  mainContent.innerHTML = `
    <form id="${eventName}-form">
      <table>
        <tbody id="tableForm">
        </tbody>
      </table>
    </form>`;
  const tableForm = document.getElementById('tableForm');
  getType(eventName).forEach(field => {
    if (field.ignore) return;
    if (field.type === 'object' && !field.list) {
      getType(field.name).forEach(subfield =>{
        tableForm.appendChild(formField(subfield))
      })
    }else{
      if(field.list){
        const row = document.createElement('tr');
        const labelCell = document.createElement('td');
        const inputCell = document.createElement('td');
        const label = document.createElement('label');
        label.textContent = capitalizeFirstLetter(field.name);
        labelCell.appendChild(label);
        const select = document.createElement('select');
        select.id = field.name + '-id';
        select.name = field.name;
        const defaultOption = document.createElement('option');
        defaultOption.value = "";
        defaultOption.textContent = "Seleccione uno";
        defaultOption.disabled = true;
        defaultOption.selected = true;
        select.appendChild(defaultOption);
        getListInfo(field.name)
        .then(data => data.forEach(obj =>{
          const option = document.createElement('option');
          option.value = obj.id;
          option.textContent = obj.nombre + " " +obj.apellido;
          select.appendChild(option);
        }));
        inputCell.appendChild(select);
        row.appendChild(labelCell);
        row.appendChild(inputCell);
        tableForm.appendChild(row);
      }else{
        tableForm.appendChild(formField(field));
      }
    }
  })
  const row = document.createElement('tr');
  const labelCell = document.createElement('td');
  const inputCell = document.createElement('td');
  const clearButton = document.createElement('button');
  clearButton.type = 'reset';
  clearButton.textContent = 'Limpiar';
  labelCell.appendChild(clearButton);
  const submitButton = document.createElement('button');
  submitButton.type = 'button';
  submitButton.textContent = 'Enviar';
  submitButton.onclick = () => registrar(eventName);
  inputCell.appendChild(submitButton);
  row.appendChild(labelCell);
  row.appendChild(inputCell);
  row.className = "buttonsRow"
  tableForm.appendChild(row)
}

function formField(field){
  const row = document.createElement('tr');
  const labelCell = document.createElement('td');
  const inputCell = document.createElement('td');
  const label = document.createElement('label');
  label.textContent = capitalizeFirstLetter(field.name);
  label.formField = field.name + '-id';
  labelCell.appendChild(label);
  const input = document.createElement('input');
  input.type = field.type;
  input.id = field.name + '-id';
  input.name = field.name;
  inputCell.appendChild(input);
  row.appendChild(labelCell);
  row.appendChild(inputCell);
  return row;
}

function registrar(eventName){
  const form = document.getElementById(eventName + '-form')
  const mainContent = document.getElementById('main-content');
  const message = document.createElement('div')
  message.className = 'message';
  let bodyRequest = {};
  getType(eventName).forEach(field =>{
    if (field.type === 'object' && !field.list) {
      let subObject = {};
      getType(field.name).forEach(subField =>{
      const inputElement = document.getElementById(subField.name + '-id');
      if (inputElement) {
        subObject[subField.name] = inputElement.value;
      }
      })
      bodyRequest[field.name] = subObject;
    }else{
      const inputElement = document.getElementById(field.name + '-id');
      if (inputElement) {
        if (field.type === 'dateTime-local'){
          
          const d = new Date(inputElement.value);
          var date_format_str = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+((parseInt(d.getMinutes()/5)*5).toString().length==2?(parseInt(d.getMinutes()/5)*5).toString():"0"+(parseInt(d.getMinutes()/5)*5).toString())+":00";
          console.log(date_format_str);
          bodyRequest[field.name] = date_format_str;          
        }else{
          bodyRequest[field.name] = inputElement.value;
        }
      }
    }
  })
  fetch(`http://localhost:8080/${eventName}s/registrar`,{
    method: 'POST',
    headers: {
      'Content-Type':'application/json'
    },
    body: JSON.stringify(bodyRequest)
  } ).then(response => response.json)
  .then(data => {
    message.innerHTML = data.message || 'Registro exitoso';
    message.className = 'success'
    mainContent.appendChild(message);
    document.getElementById(eventName+'-form').reset();
  }).catch(error => {
    message.innerHTML = 'Error al registrar: ' + error;
    message.className = 'error'
    mainContent.innerHTML = message;
  })
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