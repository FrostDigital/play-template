<div class="clearfix #{ifError _name}error#{/ifError}">
   #{if _code}<label for="${_id?:_name}">&{_code}</label>#{/if}
   <div class="input">
     <input type="input" ${_maxLength ? 'maxlength="' +  _maxLength + '"' : ''} name="${_name}" id="${_id?:_name}" class="${_class}" value="${_value}"  ${_disabled ? 'disabled="disabled"' : ''} ${_placeholder ? 'placeholder="' +  _placeholder + '"' : ''} />
     #{ifError _name}<span class="help-inline">#{error _name /}</span>#{/ifError}
   </div>
</div>
	